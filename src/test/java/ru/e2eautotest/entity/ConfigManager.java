package ru.e2eautotest.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.e2eautotest.entity.LoggerWrapper;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class ConfigManager {
    // Инициализация логера
    private static final LoggerWrapper LOG = LoggerWrapper.get(ConfigManager.class);

    private static Document document;

    public static Node evaluate (String xPathStr)
    {
        if(document == null) initDocument();

        Node node = null;
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expression = xPath.compile(xPathStr);
            node = (Node) expression.evaluate(document, XPathConstants.NODE);
            LOG.debug("Чтение из XML: " + node.getTextContent());
        } catch (XPathExpressionException e) {
            LOG.error("Неудалось прочитать из XML", e);
        }
        return node;
    }

    private static void initDocument()
    {
        LOG.debug("Инициализирую конфиг");
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().
                    newDocumentBuilder();
            document = documentBuilder.parse("src/test/resources/config.xml");
        } catch (SAXException e) {
            LOG.error(e.toString());
        } catch (IOException e) {
            LOG.error(e.toString());
        }catch (ParserConfigurationException e) {
            LOG.error(e.toString());
        }
    }
}
