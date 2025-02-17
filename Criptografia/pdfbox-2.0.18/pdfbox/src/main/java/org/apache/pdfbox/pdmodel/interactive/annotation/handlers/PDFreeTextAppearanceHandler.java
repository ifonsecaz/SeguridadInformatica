/*
 * Copyright 2018 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fontbox.util.Charsets;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.OperatorName;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDAppearanceContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import static org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLine.LE_NONE;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderEffectDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.layout.AppearanceStyle;
import org.apache.pdfbox.pdmodel.interactive.annotation.layout.PlainText;
import org.apache.pdfbox.pdmodel.interactive.annotation.layout.PlainTextFormatter;
import org.apache.pdfbox.util.Matrix;

public class PDFreeTextAppearanceHandler extends PDAbstractAppearanceHandler
{
    private static final Log LOG = LogFactory.getLog(PDFreeTextAppearanceHandler.class);

    private static final Pattern COLOR_PATTERN =
            Pattern.compile(".*color\\:\\s*\\#([0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]).*");

    private float fontSize = 10;
    private COSName fontName = COSName.HELV;

    public PDFreeTextAppearanceHandler(PDAnnotation annotation)
    {
        super(annotation);
    }

    public PDFreeTextAppearanceHandler(PDAnnotation annotation, PDDocument document)
    {
        super(annotation, document);
    }

    @Override
    public void generateAppearanceStreams()
    {
        generateNormalAppearance();
        generateRolloverAppearance();
        generateDownAppearance();
    }

    @Override
    public void generateNormalAppearance()
    {
        PDAnnotationMarkup annotation = (PDAnnotationMarkup) getAnnotation();
        float[] pathsArray = new float[0];
        if (PDAnnotationMarkup.IT_FREE_TEXT_CALLOUT.equals(annotation.getIntent()))
        {
            pathsArray = annotation.getCallout();
            if (pathsArray == null || pathsArray.length != 4 && pathsArray.length != 6)
            {
                pathsArray = new float[0];
            }
        }
        AnnotationBorder ab = AnnotationBorder.getAnnotationBorder(annotation, annotation.getBorderStyle());

        PDAppearanceContentStream cs = null;

        try
        {
            cs = getNormalAppearanceAsContentStream(true);

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
            }

            // value used by Adobe, no idea where it comes from, actual font bbox max y is 0.931
            // gathered by creating an annotation with width 0.
            float yDelta = 0.7896f;
            switch (rotation)
            {
                case 180:
                    xOffset = - borderBox.getUpperRightX() + ab.width * 2;
                    yOffset = - borderBox.getLowerLeftY() - ab.width * 2 - yDelta * fontSize;
                    clipY = - borderBox.getUpperRightY() + ab.width * 2;
                    break;
                case 90:
                    xOffset = borderBox.getLowerLeftY() + ab.width * 2;
                    yOffset = - borderBox.getLowerLeftX() - ab.width * 2 - yDelta * fontSize;
                    clipY = - borderBox.getUpperRightX() + ab.width * 2;
                    break;
                case 270:
                    xOffset = - borderBox.getUpperRightY() + ab.width * 2;
                    yOffset = borderBox.getUpperRightX() - ab.width * 2 - yDelta * fontSize;
                    clipY = borderBox.getLowerLeftX() + ab.width * 2;
                    break;
                case 0:
                default:
                    xOffset = borderBox.getLowerLeftX() + ab.width * 2;
                    yOffset = borderBox.getUpperRightY() - ab.width * 2 - yDelta * fontSize;
                    clipY = borderBox.getLowerLeftY() + ab.width * 2;
                    break;
            }

            // clip writing area
            cs.addRect(xOffset, clipY, clipWidth, clipHeight);
            cs.clip();

            cs.beginText();
            cs.setFont(font, fontSize);
            cs.setNonStrokingColor(textColor.getComponents());
            AppearanceStyle appearanceStyle = new AppearanceStyle();
            appearanceStyle.setFont(font);
            appearanceStyle.setFontSize(fontSize);
            PlainTextFormatter formatter = new PlainTextFormatter.Builder(cs)
                    .style(appearanceStyle)
                    .text(new PlainText(annotation.getContents()))
                    .width(width - ab.width * 4)
                    .wrapLines(true)
                    .initialOffset(xOffset, yOffset)
                    // Adobe ignores the /Q
                    //.textAlign(annotation.getQ())
                    .build();
            try
            {
                formatter.format();
            }
            catch (IllegalArgumentException ex)
            {
                throw new IOException(ex);
            }
            cs.endText();


            if (pathsArray.length > 0)
            {
                PDRectangle rect = getRectangle();

                // Adjust rectangle
                // important to do this after the rectangle has been painted, because the
                // final rectangle will be bigger due to callout
                // CTAN-example-Annotations.pdf p1
                //TODO in a class structure this should be overridable
                float minX = Float.MAX_VALUE;
                float minY = Float.MAX_VALUE;
                float maxX = Float.MIN_VALUE;
                float maxY = Float.MIN_VALUE;
                for (int i = 0; i < pathsArray.length / 2; ++i)
                {
                    float x = pathsArray[i * 2];
                    float y = pathsArray[i * 2 + 1];
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
                // arrow length is 9 * width at about 30° => 10 * width seems to be enough
                rect.setLowerLeftX(Math.min(minX - ab.width * 10, rect.getLowerLeftX()));
                rect.setLowerLeftY(Math.min(minY - ab.width * 10, rect.getLowerLeftY()));
                rect.setUpperRightX(Math.max(maxX + ab.width * 10, rect.getUpperRightX()));
                rect.setUpperRightY(Math.max(maxY + ab.width * 10, rect.getUpperRightY()));
                annotation.setRectangle(rect);
                
                // need to set the BBox too, because rectangle modification came later
                annotation.getNormalAppearanceStream().setBBox(getRectangle());
                
                //TODO when callout is used, /RD should be so that the result is the writable part
            }
        }
        catch (IOException ex)
        {
            LOG.error(ex);
        }
        finally
        {
            IOUtils.closeQuietly(cs);
        }
    }

    // get the last non stroking color from the /DA entry
    private PDColor extractNonStrokingColor(PDAnnotationMarkup annotation)
    {
        // It could also work with a regular expression, but that should be written so that
        // "/LucidaConsole 13.94766 Tf .392 .585 .93 rg" does not produce "2 .585 .93 rg" as result
        // Another alternative might be to create a PDDocument and a PDPage with /DA content as /Content,
        // process the whole thing and then get the non stroking color.

        PDColor strokingColor = new PDColor(new float[]{0}, PDDeviceGray.INSTANCE);
        String defaultAppearance = annotation.getDefaultAppearance();
        if (defaultAppearance == null)
        {
            return strokingColor;
        }

        try
        {
            // not sure if charset is correct, but we only need numbers and simple characters
            PDFStreamParser parser = new PDFStreamParser(defaultAppearance.getBytes(Charsets.US_ASCII));
            COSArray arguments = new COSArray();
            COSArray colors = null;
            Operator graphicOp = null;
            for (Object token = parser.parseNextToken(); token != null; token = parser.parseNextToken())
            {
                if (token instanceof COSObject)
                {
                    arguments.add(((COSObject) token).getObject());
                }
                else if (token instanceof Operator)
                {
                    Operator op = (Operator) token;
                    String name = op.getName();
                    if (OperatorName.NON_STROKING_GRAY.equals(name) ||
                        OperatorName.NON_STROKING_RGB.equals(name) ||
                        OperatorName.NON_STROKING_CMYK.equals(name))
                    {
                        graphicOp = op;
                        colors = arguments;
                    }
                    arguments = new COSArray();
                }
                else
                {
                    arguments.add((COSBase) token);
                }
            }
            if (graphicOp != null)
            {
                String graphicOpName =  graphicOp.getName();
                if (OperatorName.NON_STROKING_GRAY.equals(graphicOpName))
                {
                    strokingColor = new PDColor(colors, PDDeviceGray.INSTANCE);
                }
                else if (OperatorName.NON_STROKING_RGB.equals(graphicOpName))
                {
                    strokingColor = new PDColor(colors, PDDeviceRGB.INSTANCE);
                }
                else if (OperatorName.NON_STROKING_CMYK.equals(graphicOpName))
                {
                    strokingColor = new PDColor(colors, PDDeviceCMYK.INSTANCE);
                }
            }
        }
        catch (IOException ex)
        {
            LOG.warn("Problem parsing /DA, will use default black", ex);
        }
        return strokingColor;
    }

    //TODO extractNonStrokingColor and extractFontDetails
    // might somehow be replaced with PDDefaultAppearanceString, which is quite similar.
    private void extractFontDetails(PDAnnotationMarkup annotation)
    {
        String defaultAppearance = annotation.getDefaultAppearance();
        if (defaultAppearance == null && document != null &&
            document.getDocumentCatalog().getAcroForm() != null)
        {
            defaultAppearance = document.getDocumentCatalog().getAcroForm().getDefaultAppearance();
        }
        if (defaultAppearance == null)
        {
            return;
        }

        try
        {
            // not sure if charset is correct, but we only need numbers and simple characters
            PDFStreamParser parser = new PDFStreamParser(defaultAppearance.getBytes(Charsets.US_ASCII));
            COSArray arguments = new COSArray();
            COSArray fontArguments = new COSArray();
            for (Object token = parser.parseNextToken(); token != null; token = parser.parseNextToken())
            {
                if (token instanceof COSObject)
                {
                    arguments.add(((COSObject) token).getObject());
                }
                else if (token instanceof Operator)
                {
                    Operator op = (Operator) token;
                    String name = op.getName();
                    if (OperatorName.SET_FONT_AND_SIZE.equals(name))
                    {
                        fontArguments = arguments;
                    }
                    arguments = new COSArray();
                }
                else
                {
                    arguments.add((COSBase) token);
                }
            }
            if (fontArguments.size() >= 2)
            {
                COSBase base = fontArguments.get(0);
                if (base instanceof COSName)
                {
                    fontName = (COSName) base;
                }
                base = fontArguments.get(1);
                if (base instanceof COSNumber)
                {
                    fontSize = ((COSNumber) base).floatValue();
                }
            }
        }
        catch (IOException ex)
        {
            LOG.warn("Problem parsing /DA, will use default 'Helv 10'", ex);
        }
    }

    @Override
    public void generateRolloverAppearance()
    {
        // TODO to be implemented
    }

    @Override
    public void generateDownAppearance()
    {
        // TODO to be implemented
    }
}
