import scrapy # Importa o módulo Scrapy para criar spiders e realizar scraping de dados
from scrapy.crawler import CrawlerProcess # Importa o módulo CrawlerProcess para executar o spider de forma programática
import json # Importa o módulo JSON para salvar os dados extraídos em um arquivo JSON

class QuotesSpider(scrapy.Spider):
    name = "quotes" # Define o nome do spider, que será usado para identificá-lo ao executar o script
    start_urls = ['http://quotes.toscrape.com/'] # Define a URL inicial que o spider irá acessar para começar a extração de dados

    # Inicializamos uma lista para acumular as citações de todas as páginas
    quotes_list = []

    # Define o método parse, que é chamado automaticamente para processar a resposta da página inicial e de todas as páginas subsequentes
    def parse(self, response): 
        # Extrai as citações da página atual
        for quote in response.css('div.quote'):
            quote_data = {
                'text': quote.css('span.text::text').get().strip(),
                'author': quote.css('small.author::text').get(),
                'tags': quote.css('div.tags a.tag::text').getall(),
            }
            self.quotes_list.append(quote_data)

        # Caso haja próxima página
        next_page = response.css('li.next a::attr(href)').get()
        if next_page is not None:
            # Segue para a próxima página
            yield response.follow(next_page, self.parse)
        else:
            # Quando não há mais páginas, salva os dados acumulados em um arquivo JSON
            with open('quotes.json', 'w', encoding='utf-8') as f:
                json.dump(self.quotes_list, f, ensure_ascii=False, indent=4)

# Executa o spider
process = CrawlerProcess()
process.crawl(QuotesSpider)
process.start()