package com.courses.courselms.services;

public abstract class HtmlGenerator {

    public final String generate(String activationCode) {
        StringBuilder html = new StringBuilder();
        String header = this.createHeader();
        String body = this.createBody(activationCode);
        String footer = this.createFooter();
        html.append(header)
                .append(body)
                .append(footer);
        return html.toString();
    }
    protected abstract String createHeader();
    protected abstract String createBody(String activationCode);

    protected abstract String createFooter();
}
