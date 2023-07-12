package com.courses.courselms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class WelcomeHtmlGenerator extends HtmlGenerator {

    @Value("${api.web_host}")
    private String host;

    @Value("${server.port}")
    private int port;
    @Override
    protected String createHeader() {
        return """
                <h1>Olá, seu código de ativação chegou!</h1>
                <hr />
                """;
    }

    @Override
    protected String createBody(String activationCode) {
        StringBuilder builder = new StringBuilder();
        String text = "Clique no link abaixo para ativar: <br />";
        String url = String.format("<a href=\"%s\">Ative aqui!</a>",
                this.host + ":" + this.port + "/activation" + "/" + activationCode
                );
        return builder.append(text).append(url).toString();
    }

    @Override
    protected String createFooter() {
        return "<br />Atenciosamente, <br />Rafa Tedesco";
    }
}
