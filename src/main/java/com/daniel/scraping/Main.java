package com.daniel.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    private static final String URL =
            "https://www.fundamentus.com.br/detalhes.php?papel=PETR4";

    public static void main(String[] args) {

        try {

            Document document = connect(URL);

            printPreco(document);
            printDetalhes(document);

        } catch (IOException e) {
            System.err.println("Erro ao acessar a página: " + e.getMessage());
        }
    }

    private static Document connect(String url) throws IOException {

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();
    }

    private static void printPreco(Document document) {

        Element precoElement = document.selectFirst("td.data.destaque.w3");

        String preco = precoElement != null
                ? precoElement.text()
                : "N/A";

        System.out.println("Preço da Ação: " + preco);
        System.out.println("---------------------------");
    }

    private static void printDetalhes(Document document) {

        Elements details = document.select(".w728 tbody tr");

        for (Element row : details) {

            String key = row.select("td:nth-child(1)").text();
            String value = row.select("td:nth-child(2)").text();

            if (!key.isEmpty()) {
                System.out.printf("%-20s : %s%n", key, value);
            }
        }
    }
}