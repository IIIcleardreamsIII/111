package com.example.myblog.demos.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;


public class MarkdownUtils {

    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(new MutableDataSet()).build();
    private static final Parser PARSER = Parser.builder(new MutableDataSet()).build();

    public static String markdownToHtml(String markdown) {
        return RENDERER.render(PARSER.parse(markdown));
    }
}
