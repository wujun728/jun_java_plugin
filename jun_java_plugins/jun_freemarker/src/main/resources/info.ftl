<?xml version="1.0" encoding="utf-8"?>
<?mso-application progid="Word.Document"?>

<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
  <pkg:part pkg:name="/_rels/.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:padding="512">
    <pkg:xmlData>
      <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
        <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
        <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
        <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
      </Relationships>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/_rels/document.xml.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:padding="256">
    <pkg:xmlData>
      <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
        <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings" Target="settings.xml"/>
        <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
        <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXml" Target="../customXml/item1.xml"/>
        <Relationship Id="rId6" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme" Target="theme/theme1.xml"/>
        <Relationship Id="rId5" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable" Target="fontTable.xml"/>
        <Relationship Id="rId4" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings" Target="webSettings.xml"/>
      </Relationships>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/document.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml">
    <pkg:xmlData>
      <w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup" xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk" xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml" xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape" mc:Ignorable="w14 w15 wp14">
        <w:body>
			<#list tables as t>
          <w:p w:rsidR="00F45F7F" w:rsidRDefault="00313E87">
            <w:r>
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
              </w:rPr>
              <w:t>${t_index+1}.</w:t>
            </w:r>
            <w:r>
              <w:t xml:space="preserve">${t.tableName}</w:t>
            </w:r>
			 <w:r>
              <w:t xml:space="preserve">${t.tableRemarks}</w:t>
            </w:r>
          </w:p>
		 
          <w:tbl>
            <w:tblPr>
              <w:tblStyle w:val="a4"/>
              <w:tblW w:w="0" w:type="auto"/>
              <w:tblLook w:val="04A0" w:firstRow="1" w:lastRow="0" w:firstColumn="1" w:lastColumn="0" w:noHBand="0" w:noVBand="1"/>
            </w:tblPr>
            <w:tblGrid>
              <w:gridCol w:w="1382"/>
              <w:gridCol w:w="1382"/>
              <w:gridCol w:w="1383"/>
              <w:gridCol w:w="1383"/>
              <w:gridCol w:w="1383"/>
              <w:gridCol w:w="1383"/>
            </w:tblGrid>
            <w:tr w:rsidR="00313E87" w:rsidTr="00313E87">
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1382" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>序号</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1382" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>列名</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>注释</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>类型</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>是否为空</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                </w:p>
              </w:tc>
            </w:tr>
             <#list t.list as p>
            <w:tr w:rsidR="00313E87" w:rsidTr="00313E87">
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1382" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00C66B36">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>${p_index+1}</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1382" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00C66B36">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>${p.columnName}</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00C66B36">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>${p.REMARKS}</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00C66B36">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>${p.columnType}</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00C66B36">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                  <w:r>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t>${p.nullable}</w:t>
                  </w:r>
                </w:p>
              </w:tc>
              <w:tc>
                <w:tcPr>
                  <w:tcW w:w="1383" w:type="dxa"/>
                </w:tcPr>
                <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
                  <w:pPr>
                    <w:rPr>
                      <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                  </w:pPr>
                </w:p>
              </w:tc>
            </w:tr>
            </#list>
          </w:tbl>
		  </#list>
          <w:p w:rsidR="00313E87" w:rsidRDefault="00313E87">
            <w:pPr>
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
              </w:rPr>
            </w:pPr>
          </w:p>
          <w:sectPr w:rsidR="00313E87">
            <w:pgSz w:w="11906" w:h="16838"/>
            <w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="851" w:footer="992" w:gutter="0"/>
            <w:cols w:space="425"/>
            <w:docGrid w:type="lines" w:linePitch="312"/>
          </w:sectPr>
        </w:body>
      </w:document>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/theme/theme1.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.theme+xml">
    <pkg:xmlData>
      <a:theme xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" name="Office 主题">
        <a:themeElements>
          <a:clrScheme name="Office">
            <a:dk1>
              <a:sysClr val="windowText" lastClr="000000"/>
            </a:dk1>
            <a:lt1>
              <a:sysClr val="window" lastClr="FFFFFF"/>
            </a:lt1>
            <a:dk2>
              <a:srgbClr val="44546A"/>
            </a:dk2>
            <a:lt2>
              <a:srgbClr val="E7E6E6"/>
            </a:lt2>
            <a:accent1>
              <a:srgbClr val="5B9BD5"/>
            </a:accent1>
            <a:accent2>
              <a:srgbClr val="ED7D31"/>
            </a:accent2>
            <a:accent3>
              <a:srgbClr val="A5A5A5"/>
            </a:accent3>
            <a:accent4>
              <a:srgbClr val="FFC000"/>
            </a:accent4>
            <a:accent5>
              <a:srgbClr val="4472C4"/>
            </a:accent5>
            <a:accent6>
              <a:srgbClr val="70AD47"/>
            </a:accent6>
            <a:hlink>
              <a:srgbClr val="0563C1"/>
            </a:hlink>
            <a:folHlink>
              <a:srgbClr val="954F72"/>
            </a:folHlink>
          </a:clrScheme>
          <a:fontScheme name="Office">
            <a:majorFont>
              <a:latin typeface="Calibri Light" panose="020F0302020204030204"/>
              <a:ea typeface=""/>
              <a:cs typeface=""/>
              <a:font script="Jpan" typeface="ＭＳ ゴシック"/>
              <a:font script="Hang" typeface="맑은 고딕"/>
              <a:font script="Hans" typeface="宋体"/>
              <a:font script="Hant" typeface="新細明體"/>
              <a:font script="Arab" typeface="Times New Roman"/>
              <a:font script="Hebr" typeface="Times New Roman"/>
              <a:font script="Thai" typeface="Angsana New"/>
              <a:font script="Ethi" typeface="Nyala"/>
              <a:font script="Beng" typeface="Vrinda"/>
              <a:font script="Gujr" typeface="Shruti"/>
              <a:font script="Khmr" typeface="MoolBoran"/>
              <a:font script="Knda" typeface="Tunga"/>
              <a:font script="Guru" typeface="Raavi"/>
              <a:font script="Cans" typeface="Euphemia"/>
              <a:font script="Cher" typeface="Plantagenet Cherokee"/>
              <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
              <a:font script="Tibt" typeface="Microsoft Himalaya"/>
              <a:font script="Thaa" typeface="MV Boli"/>
              <a:font script="Deva" typeface="Mangal"/>
              <a:font script="Telu" typeface="Gautami"/>
              <a:font script="Taml" typeface="Latha"/>
              <a:font script="Syrc" typeface="Estrangelo Edessa"/>
              <a:font script="Orya" typeface="Kalinga"/>
              <a:font script="Mlym" typeface="Kartika"/>
              <a:font script="Laoo" typeface="DokChampa"/>
              <a:font script="Sinh" typeface="Iskoola Pota"/>
              <a:font script="Mong" typeface="Mongolian Baiti"/>
              <a:font script="Viet" typeface="Times New Roman"/>
              <a:font script="Uigh" typeface="Microsoft Uighur"/>
              <a:font script="Geor" typeface="Sylfaen"/>
            </a:majorFont>
            <a:minorFont>
              <a:latin typeface="Calibri" panose="020F0502020204030204"/>
              <a:ea typeface=""/>
              <a:cs typeface=""/>
              <a:font script="Jpan" typeface="ＭＳ 明朝"/>
              <a:font script="Hang" typeface="맑은 고딕"/>
              <a:font script="Hans" typeface="宋体"/>
              <a:font script="Hant" typeface="新細明體"/>
              <a:font script="Arab" typeface="Arial"/>
              <a:font script="Hebr" typeface="Arial"/>
              <a:font script="Thai" typeface="Cordia New"/>
              <a:font script="Ethi" typeface="Nyala"/>
              <a:font script="Beng" typeface="Vrinda"/>
              <a:font script="Gujr" typeface="Shruti"/>
              <a:font script="Khmr" typeface="DaunPenh"/>
              <a:font script="Knda" typeface="Tunga"/>
              <a:font script="Guru" typeface="Raavi"/>
              <a:font script="Cans" typeface="Euphemia"/>
              <a:font script="Cher" typeface="Plantagenet Cherokee"/>
              <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
              <a:font script="Tibt" typeface="Microsoft Himalaya"/>
              <a:font script="Thaa" typeface="MV Boli"/>
              <a:font script="Deva" typeface="Mangal"/>
              <a:font script="Telu" typeface="Gautami"/>
              <a:font script="Taml" typeface="Latha"/>
              <a:font script="Syrc" typeface="Estrangelo Edessa"/>
              <a:font script="Orya" typeface="Kalinga"/>
              <a:font script="Mlym" typeface="Kartika"/>
              <a:font script="Laoo" typeface="DokChampa"/>
              <a:font script="Sinh" typeface="Iskoola Pota"/>
              <a:font script="Mong" typeface="Mongolian Baiti"/>
              <a:font script="Viet" typeface="Arial"/>
              <a:font script="Uigh" typeface="Microsoft Uighur"/>
              <a:font script="Geor" typeface="Sylfaen"/>
            </a:minorFont>
          </a:fontScheme>
          <a:fmtScheme name="Office">
            <a:fillStyleLst>
              <a:solidFill>
                <a:schemeClr val="phClr"/>
              </a:solidFill>
              <a:gradFill rotWithShape="1">
                <a:gsLst>
                  <a:gs pos="0">
                    <a:schemeClr val="phClr">
                      <a:lumMod val="110000"/>
                      <a:satMod val="105000"/>
                      <a:tint val="67000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="50000">
                    <a:schemeClr val="phClr">
                      <a:lumMod val="105000"/>
                      <a:satMod val="103000"/>
                      <a:tint val="73000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="100000">
                    <a:schemeClr val="phClr">
                      <a:lumMod val="105000"/>
                      <a:satMod val="109000"/>
                      <a:tint val="81000"/>
                    </a:schemeClr>
                  </a:gs>
                </a:gsLst>
                <a:lin ang="5400000" scaled="0"/>
              </a:gradFill>
              <a:gradFill rotWithShape="1">
                <a:gsLst>
                  <a:gs pos="0">
                    <a:schemeClr val="phClr">
                      <a:satMod val="103000"/>
                      <a:lumMod val="102000"/>
                      <a:tint val="94000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="50000">
                    <a:schemeClr val="phClr">
                      <a:satMod val="110000"/>
                      <a:lumMod val="100000"/>
                      <a:shade val="100000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="100000">
                    <a:schemeClr val="phClr">
                      <a:lumMod val="99000"/>
                      <a:satMod val="120000"/>
                      <a:shade val="78000"/>
                    </a:schemeClr>
                  </a:gs>
                </a:gsLst>
                <a:lin ang="5400000" scaled="0"/>
              </a:gradFill>
            </a:fillStyleLst>
            <a:lnStyleLst>
              <a:ln w="6350" cap="flat" cmpd="sng" algn="ctr">
                <a:solidFill>
                  <a:schemeClr val="phClr"/>
                </a:solidFill>
                <a:prstDash val="solid"/>
                <a:miter lim="800000"/>
              </a:ln>
              <a:ln w="12700" cap="flat" cmpd="sng" algn="ctr">
                <a:solidFill>
                  <a:schemeClr val="phClr"/>
                </a:solidFill>
                <a:prstDash val="solid"/>
                <a:miter lim="800000"/>
              </a:ln>
              <a:ln w="19050" cap="flat" cmpd="sng" algn="ctr">
                <a:solidFill>
                  <a:schemeClr val="phClr"/>
                </a:solidFill>
                <a:prstDash val="solid"/>
                <a:miter lim="800000"/>
              </a:ln>
            </a:lnStyleLst>
            <a:effectStyleLst>
              <a:effectStyle>
                <a:effectLst/>
              </a:effectStyle>
              <a:effectStyle>
                <a:effectLst/>
              </a:effectStyle>
              <a:effectStyle>
                <a:effectLst>
                  <a:outerShdw blurRad="57150" dist="19050" dir="5400000" algn="ctr" rotWithShape="0">
                    <a:srgbClr val="000000">
                      <a:alpha val="63000"/>
                    </a:srgbClr>
                  </a:outerShdw>
                </a:effectLst>
              </a:effectStyle>
            </a:effectStyleLst>
            <a:bgFillStyleLst>
              <a:solidFill>
                <a:schemeClr val="phClr"/>
              </a:solidFill>
              <a:solidFill>
                <a:schemeClr val="phClr">
                  <a:tint val="95000"/>
                  <a:satMod val="170000"/>
                </a:schemeClr>
              </a:solidFill>
              <a:gradFill rotWithShape="1">
                <a:gsLst>
                  <a:gs pos="0">
                    <a:schemeClr val="phClr">
                      <a:tint val="93000"/>
                      <a:satMod val="150000"/>
                      <a:shade val="98000"/>
                      <a:lumMod val="102000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="50000">
                    <a:schemeClr val="phClr">
                      <a:tint val="98000"/>
                      <a:satMod val="130000"/>
                      <a:shade val="90000"/>
                      <a:lumMod val="103000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="100000">
                    <a:schemeClr val="phClr">
                      <a:shade val="63000"/>
                      <a:satMod val="120000"/>
                    </a:schemeClr>
                  </a:gs>
                </a:gsLst>
                <a:lin ang="5400000" scaled="0"/>
              </a:gradFill>
            </a:bgFillStyleLst>
          </a:fmtScheme>
        </a:themeElements>
        <a:objectDefaults/>
        <a:extraClrSchemeLst/>
        <a:extLst>
          <a:ext uri="{05A4C25C-085E-4340-85A3-A5531E510DB2}">
            <thm15:themeFamily xmlns:thm15="http://schemas.microsoft.com/office/thememl/2012/main" name="Office Theme" id="{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}" vid="{4A3C46E8-61CC-4603-A589-7422A47A8E4A}"></thm15:themeFamily>
          </a:ext>
        </a:extLst>
      </a:theme>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/settings.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml">
    <pkg:xmlData>
      <w:settings xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main" mc:Ignorable="w14 w15">
        <w:zoom w:percent="100"/>
        <w:bordersDoNotSurroundHeader/>
        <w:bordersDoNotSurroundFooter/>
        <w:proofState w:spelling="clean" w:grammar="clean"/>
        <w:defaultTabStop w:val="420"/>
        <w:drawingGridVerticalSpacing w:val="156"/>
        <w:displayHorizontalDrawingGridEvery w:val="0"/>
        <w:displayVerticalDrawingGridEvery w:val="2"/>
        <w:characterSpacingControl w:val="compressPunctuation"/>
        <w:compat>
          <w:spaceForUL/>
          <w:balanceSingleByteDoubleByteWidth/>
          <w:doNotLeaveBackslashAlone/>
          <w:ulTrailSpace/>
          <w:doNotExpandShiftReturn/>
          <w:adjustLineHeightInTable/>
          <w:useFELayout/>
          <w:compatSetting w:name="compatibilityMode" w:uri="http://schemas.microsoft.com/office/word" w:val="15"/>
          <w:compatSetting w:name="overrideTableStyleFontSizeAndJustification" w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
          <w:compatSetting w:name="enableOpenTypeFeatures" w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
          <w:compatSetting w:name="doNotFlipMirrorIndents" w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
          <w:compatSetting w:name="differentiateMultirowTableHeaders" w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
        </w:compat>
        <w:rsids>
          <w:rsidRoot w:val="00F60178"/>
          <w:rsid w:val="00313E87"/>
          <w:rsid w:val="00C66B36"/>
          <w:rsid w:val="00F45F7F"/>
          <w:rsid w:val="00F60178"/>
        </w:rsids>
        <m:mathPr>
          <m:mathFont m:val="Cambria Math"/>
          <m:brkBin m:val="before"/>
          <m:brkBinSub m:val="--"/>
          <m:smallFrac m:val="0"/>
          <m:dispDef/>
          <m:lMargin m:val="0"/>
          <m:rMargin m:val="0"/>
          <m:defJc m:val="centerGroup"/>
          <m:wrapIndent m:val="1440"/>
          <m:intLim m:val="subSup"/>
          <m:naryLim m:val="undOvr"/>
        </m:mathPr>
        <w:themeFontLang w:val="en-US" w:eastAsia="zh-CN"/>
        <w:clrSchemeMapping w:bg1="light1" w:t1="dark1" w:bg2="light2" w:t2="dark2" w:accent1="accent1" w:accent2="accent2" w:accent3="accent3" w:accent4="accent4" w:accent5="accent5" w:accent6="accent6" w:hyperlink="hyperlink" w:followedHyperlink="followedHyperlink"/>
        <w:shapeDefaults>
          <o:shapedefaults v:ext="edit" spidmax="1026"/>
          <o:shapelayout v:ext="edit">
            <o:idmap v:ext="edit" data="1"/>
          </o:shapelayout>
        </w:shapeDefaults>
        <w:decimalSymbol w:val="."/>
        <w:listSeparator w:val=","/>
        <w15:chartTrackingRefBased/>
        <w15:docId w15:val="{EE4453EA-A7E3-475A-AA4F-FBFC036EBC74}"/>
      </w:settings>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/customXml/_rels/item1.xml.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:padding="256">
    <pkg:xmlData>
      <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
        <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXmlProps" Target="itemProps1.xml"/>
      </Relationships>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/customXml/itemProps1.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.customXmlProperties+xml" pkg:padding="32">
    <pkg:xmlData pkg:originalXmlStandalone="no">
      <ds:datastoreItem xmlns:ds="http://schemas.openxmlformats.org/officeDocument/2006/customXml" ds:itemID="{68351986-9288-4408-8BCB-EB0848120278}">
        <ds:schemaRefs>
          <ds:schemaRef ds:uri="http://schemas.openxmlformats.org/officeDocument/2006/bibliography"/>
        </ds:schemaRefs>
      </ds:datastoreItem>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/styles.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml">
    <pkg:xmlData>
      <w:styles xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" mc:Ignorable="w14 w15">
        <w:docDefaults>
          <w:rPrDefault>
            <w:rPr>
              <w:rFonts w:asciiTheme="minorHAnsi" w:eastAsiaTheme="minorEastAsia" w:hAnsiTheme="minorHAnsi" w:cstheme="minorBidi"/>
              <w:kern w:val="2"/>
              <w:sz w:val="21"/>
              <w:szCs w:val="22"/>
              <w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
            </w:rPr>
          </w:rPrDefault>
          <w:pPrDefault/>
        </w:docDefaults>
        <w:latentStyles w:defLockedState="0" w:defUIPriority="99" w:defSemiHidden="0" w:defUnhideWhenUsed="0" w:defQFormat="0" w:count="371">
          <w:lsdException w:name="Normal" w:uiPriority="0" w:qFormat="1"/>
          <w:lsdException w:name="heading 1" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 2" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="heading 3" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="heading 4" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="heading 5" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="heading 6" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="heading 7" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="heading 8" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="heading 9" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="index 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 6" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 7" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 8" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index 9" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 1" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 2" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 3" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 4" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 5" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 6" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 7" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 8" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toc 9" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Normal Indent" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="footnote text" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="annotation text" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="header" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="footer" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="index heading" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="caption" w:semiHidden="1" w:uiPriority="35" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="table of figures" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="envelope address" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="envelope return" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="footnote reference" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="annotation reference" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="line number" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="page number" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="endnote reference" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="endnote text" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="table of authorities" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="macro" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="toa heading" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Bullet" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Number" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Bullet 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Bullet 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Bullet 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Bullet 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Number 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Number 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Number 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Number 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Title" w:uiPriority="10" w:qFormat="1"/>
          <w:lsdException w:name="Closing" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Signature" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Default Paragraph Font" w:semiHidden="1" w:uiPriority="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text Indent" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Continue" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Continue 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Continue 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Continue 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="List Continue 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Message Header" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Subtitle" w:uiPriority="11" w:qFormat="1"/>
          <w:lsdException w:name="Salutation" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Date" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text First Indent" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text First Indent 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Note Heading" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text Indent 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Body Text Indent 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Block Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Hyperlink" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="FollowedHyperlink" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Strong" w:uiPriority="22" w:qFormat="1"/>
          <w:lsdException w:name="Emphasis" w:uiPriority="20" w:qFormat="1"/>
          <w:lsdException w:name="Document Map" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Plain Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="E-mail Signature" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Top of Form" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Bottom of Form" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Normal (Web)" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Acronym" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Address" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Cite" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Code" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Definition" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Keyboard" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Preformatted" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Sample" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Typewriter" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="HTML Variable" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Normal Table" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="annotation subject" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="No List" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Outline List 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Outline List 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Outline List 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Simple 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Simple 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Simple 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Classic 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Classic 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Classic 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Classic 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Colorful 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Colorful 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Colorful 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Columns 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Columns 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Columns 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Columns 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Columns 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 6" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 7" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid 8" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 6" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 7" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table List 8" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table 3D effects 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table 3D effects 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table 3D effects 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Contemporary" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Elegant" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Professional" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Subtle 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Subtle 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Web 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Web 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Web 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Balloon Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Table Grid" w:uiPriority="39"/>
          <w:lsdException w:name="Table Theme" w:semiHidden="1" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="Placeholder Text" w:semiHidden="1"/>
          <w:lsdException w:name="No Spacing" w:uiPriority="1" w:qFormat="1"/>
          <w:lsdException w:name="Light Shading" w:uiPriority="60"/>
          <w:lsdException w:name="Light List" w:uiPriority="61"/>
          <w:lsdException w:name="Light Grid" w:uiPriority="62"/>
          <w:lsdException w:name="Medium Shading 1" w:uiPriority="63"/>
          <w:lsdException w:name="Medium Shading 2" w:uiPriority="64"/>
          <w:lsdException w:name="Medium List 1" w:uiPriority="65"/>
          <w:lsdException w:name="Medium List 2" w:uiPriority="66"/>
          <w:lsdException w:name="Medium Grid 1" w:uiPriority="67"/>
          <w:lsdException w:name="Medium Grid 2" w:uiPriority="68"/>
          <w:lsdException w:name="Medium Grid 3" w:uiPriority="69"/>
          <w:lsdException w:name="Dark List" w:uiPriority="70"/>
          <w:lsdException w:name="Colorful Shading" w:uiPriority="71"/>
          <w:lsdException w:name="Colorful List" w:uiPriority="72"/>
          <w:lsdException w:name="Colorful Grid" w:uiPriority="73"/>
          <w:lsdException w:name="Light Shading Accent 1" w:uiPriority="60"/>
          <w:lsdException w:name="Light List Accent 1" w:uiPriority="61"/>
          <w:lsdException w:name="Light Grid Accent 1" w:uiPriority="62"/>
          <w:lsdException w:name="Medium Shading 1 Accent 1" w:uiPriority="63"/>
          <w:lsdException w:name="Medium Shading 2 Accent 1" w:uiPriority="64"/>
          <w:lsdException w:name="Medium List 1 Accent 1" w:uiPriority="65"/>
          <w:lsdException w:name="Revision" w:semiHidden="1"/>
          <w:lsdException w:name="List Paragraph" w:uiPriority="34" w:qFormat="1"/>
          <w:lsdException w:name="Quote" w:uiPriority="29" w:qFormat="1"/>
          <w:lsdException w:name="Intense Quote" w:uiPriority="30" w:qFormat="1"/>
          <w:lsdException w:name="Medium List 2 Accent 1" w:uiPriority="66"/>
          <w:lsdException w:name="Medium Grid 1 Accent 1" w:uiPriority="67"/>
          <w:lsdException w:name="Medium Grid 2 Accent 1" w:uiPriority="68"/>
          <w:lsdException w:name="Medium Grid 3 Accent 1" w:uiPriority="69"/>
          <w:lsdException w:name="Dark List Accent 1" w:uiPriority="70"/>
          <w:lsdException w:name="Colorful Shading Accent 1" w:uiPriority="71"/>
          <w:lsdException w:name="Colorful List Accent 1" w:uiPriority="72"/>
          <w:lsdException w:name="Colorful Grid Accent 1" w:uiPriority="73"/>
          <w:lsdException w:name="Light Shading Accent 2" w:uiPriority="60"/>
          <w:lsdException w:name="Light List Accent 2" w:uiPriority="61"/>
          <w:lsdException w:name="Light Grid Accent 2" w:uiPriority="62"/>
          <w:lsdException w:name="Medium Shading 1 Accent 2" w:uiPriority="63"/>
          <w:lsdException w:name="Medium Shading 2 Accent 2" w:uiPriority="64"/>
          <w:lsdException w:name="Medium List 1 Accent 2" w:uiPriority="65"/>
          <w:lsdException w:name="Medium List 2 Accent 2" w:uiPriority="66"/>
          <w:lsdException w:name="Medium Grid 1 Accent 2" w:uiPriority="67"/>
          <w:lsdException w:name="Medium Grid 2 Accent 2" w:uiPriority="68"/>
          <w:lsdException w:name="Medium Grid 3 Accent 2" w:uiPriority="69"/>
          <w:lsdException w:name="Dark List Accent 2" w:uiPriority="70"/>
          <w:lsdException w:name="Colorful Shading Accent 2" w:uiPriority="71"/>
          <w:lsdException w:name="Colorful List Accent 2" w:uiPriority="72"/>
          <w:lsdException w:name="Colorful Grid Accent 2" w:uiPriority="73"/>
          <w:lsdException w:name="Light Shading Accent 3" w:uiPriority="60"/>
          <w:lsdException w:name="Light List Accent 3" w:uiPriority="61"/>
          <w:lsdException w:name="Light Grid Accent 3" w:uiPriority="62"/>
          <w:lsdException w:name="Medium Shading 1 Accent 3" w:uiPriority="63"/>
          <w:lsdException w:name="Medium Shading 2 Accent 3" w:uiPriority="64"/>
          <w:lsdException w:name="Medium List 1 Accent 3" w:uiPriority="65"/>
          <w:lsdException w:name="Medium List 2 Accent 3" w:uiPriority="66"/>
          <w:lsdException w:name="Medium Grid 1 Accent 3" w:uiPriority="67"/>
          <w:lsdException w:name="Medium Grid 2 Accent 3" w:uiPriority="68"/>
          <w:lsdException w:name="Medium Grid 3 Accent 3" w:uiPriority="69"/>
          <w:lsdException w:name="Dark List Accent 3" w:uiPriority="70"/>
          <w:lsdException w:name="Colorful Shading Accent 3" w:uiPriority="71"/>
          <w:lsdException w:name="Colorful List Accent 3" w:uiPriority="72"/>
          <w:lsdException w:name="Colorful Grid Accent 3" w:uiPriority="73"/>
          <w:lsdException w:name="Light Shading Accent 4" w:uiPriority="60"/>
          <w:lsdException w:name="Light List Accent 4" w:uiPriority="61"/>
          <w:lsdException w:name="Light Grid Accent 4" w:uiPriority="62"/>
          <w:lsdException w:name="Medium Shading 1 Accent 4" w:uiPriority="63"/>
          <w:lsdException w:name="Medium Shading 2 Accent 4" w:uiPriority="64"/>
          <w:lsdException w:name="Medium List 1 Accent 4" w:uiPriority="65"/>
          <w:lsdException w:name="Medium List 2 Accent 4" w:uiPriority="66"/>
          <w:lsdException w:name="Medium Grid 1 Accent 4" w:uiPriority="67"/>
          <w:lsdException w:name="Medium Grid 2 Accent 4" w:uiPriority="68"/>
          <w:lsdException w:name="Medium Grid 3 Accent 4" w:uiPriority="69"/>
          <w:lsdException w:name="Dark List Accent 4" w:uiPriority="70"/>
          <w:lsdException w:name="Colorful Shading Accent 4" w:uiPriority="71"/>
          <w:lsdException w:name="Colorful List Accent 4" w:uiPriority="72"/>
          <w:lsdException w:name="Colorful Grid Accent 4" w:uiPriority="73"/>
          <w:lsdException w:name="Light Shading Accent 5" w:uiPriority="60"/>
          <w:lsdException w:name="Light List Accent 5" w:uiPriority="61"/>
          <w:lsdException w:name="Light Grid Accent 5" w:uiPriority="62"/>
          <w:lsdException w:name="Medium Shading 1 Accent 5" w:uiPriority="63"/>
          <w:lsdException w:name="Medium Shading 2 Accent 5" w:uiPriority="64"/>
          <w:lsdException w:name="Medium List 1 Accent 5" w:uiPriority="65"/>
          <w:lsdException w:name="Medium List 2 Accent 5" w:uiPriority="66"/>
          <w:lsdException w:name="Medium Grid 1 Accent 5" w:uiPriority="67"/>
          <w:lsdException w:name="Medium Grid 2 Accent 5" w:uiPriority="68"/>
          <w:lsdException w:name="Medium Grid 3 Accent 5" w:uiPriority="69"/>
          <w:lsdException w:name="Dark List Accent 5" w:uiPriority="70"/>
          <w:lsdException w:name="Colorful Shading Accent 5" w:uiPriority="71"/>
          <w:lsdException w:name="Colorful List Accent 5" w:uiPriority="72"/>
          <w:lsdException w:name="Colorful Grid Accent 5" w:uiPriority="73"/>
          <w:lsdException w:name="Light Shading Accent 6" w:uiPriority="60"/>
          <w:lsdException w:name="Light List Accent 6" w:uiPriority="61"/>
          <w:lsdException w:name="Light Grid Accent 6" w:uiPriority="62"/>
          <w:lsdException w:name="Medium Shading 1 Accent 6" w:uiPriority="63"/>
          <w:lsdException w:name="Medium Shading 2 Accent 6" w:uiPriority="64"/>
          <w:lsdException w:name="Medium List 1 Accent 6" w:uiPriority="65"/>
          <w:lsdException w:name="Medium List 2 Accent 6" w:uiPriority="66"/>
          <w:lsdException w:name="Medium Grid 1 Accent 6" w:uiPriority="67"/>
          <w:lsdException w:name="Medium Grid 2 Accent 6" w:uiPriority="68"/>
          <w:lsdException w:name="Medium Grid 3 Accent 6" w:uiPriority="69"/>
          <w:lsdException w:name="Dark List Accent 6" w:uiPriority="70"/>
          <w:lsdException w:name="Colorful Shading Accent 6" w:uiPriority="71"/>
          <w:lsdException w:name="Colorful List Accent 6" w:uiPriority="72"/>
          <w:lsdException w:name="Colorful Grid Accent 6" w:uiPriority="73"/>
          <w:lsdException w:name="Subtle Emphasis" w:uiPriority="19" w:qFormat="1"/>
          <w:lsdException w:name="Intense Emphasis" w:uiPriority="21" w:qFormat="1"/>
          <w:lsdException w:name="Subtle Reference" w:uiPriority="31" w:qFormat="1"/>
          <w:lsdException w:name="Intense Reference" w:uiPriority="32" w:qFormat="1"/>
          <w:lsdException w:name="Book Title" w:uiPriority="33" w:qFormat="1"/>
          <w:lsdException w:name="Bibliography" w:semiHidden="1" w:uiPriority="37" w:unhideWhenUsed="1"/>
          <w:lsdException w:name="TOC Heading" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1" w:qFormat="1"/>
          <w:lsdException w:name="Plain Table 1" w:uiPriority="41"/>
          <w:lsdException w:name="Plain Table 2" w:uiPriority="42"/>
          <w:lsdException w:name="Plain Table 3" w:uiPriority="43"/>
          <w:lsdException w:name="Plain Table 4" w:uiPriority="44"/>
          <w:lsdException w:name="Plain Table 5" w:uiPriority="45"/>
          <w:lsdException w:name="Grid Table Light" w:uiPriority="40"/>
          <w:lsdException w:name="Grid Table 1 Light" w:uiPriority="46"/>
          <w:lsdException w:name="Grid Table 2" w:uiPriority="47"/>
          <w:lsdException w:name="Grid Table 3" w:uiPriority="48"/>
          <w:lsdException w:name="Grid Table 4" w:uiPriority="49"/>
          <w:lsdException w:name="Grid Table 5 Dark" w:uiPriority="50"/>
          <w:lsdException w:name="Grid Table 6 Colorful" w:uiPriority="51"/>
          <w:lsdException w:name="Grid Table 7 Colorful" w:uiPriority="52"/>
          <w:lsdException w:name="Grid Table 1 Light Accent 1" w:uiPriority="46"/>
          <w:lsdException w:name="Grid Table 2 Accent 1" w:uiPriority="47"/>
          <w:lsdException w:name="Grid Table 3 Accent 1" w:uiPriority="48"/>
          <w:lsdException w:name="Grid Table 4 Accent 1" w:uiPriority="49"/>
          <w:lsdException w:name="Grid Table 5 Dark Accent 1" w:uiPriority="50"/>
          <w:lsdException w:name="Grid Table 6 Colorful Accent 1" w:uiPriority="51"/>
          <w:lsdException w:name="Grid Table 7 Colorful Accent 1" w:uiPriority="52"/>
          <w:lsdException w:name="Grid Table 1 Light Accent 2" w:uiPriority="46"/>
          <w:lsdException w:name="Grid Table 2 Accent 2" w:uiPriority="47"/>
          <w:lsdException w:name="Grid Table 3 Accent 2" w:uiPriority="48"/>
          <w:lsdException w:name="Grid Table 4 Accent 2" w:uiPriority="49"/>
          <w:lsdException w:name="Grid Table 5 Dark Accent 2" w:uiPriority="50"/>
          <w:lsdException w:name="Grid Table 6 Colorful Accent 2" w:uiPriority="51"/>
          <w:lsdException w:name="Grid Table 7 Colorful Accent 2" w:uiPriority="52"/>
          <w:lsdException w:name="Grid Table 1 Light Accent 3" w:uiPriority="46"/>
          <w:lsdException w:name="Grid Table 2 Accent 3" w:uiPriority="47"/>
          <w:lsdException w:name="Grid Table 3 Accent 3" w:uiPriority="48"/>
          <w:lsdException w:name="Grid Table 4 Accent 3" w:uiPriority="49"/>
          <w:lsdException w:name="Grid Table 5 Dark Accent 3" w:uiPriority="50"/>
          <w:lsdException w:name="Grid Table 6 Colorful Accent 3" w:uiPriority="51"/>
          <w:lsdException w:name="Grid Table 7 Colorful Accent 3" w:uiPriority="52"/>
          <w:lsdException w:name="Grid Table 1 Light Accent 4" w:uiPriority="46"/>
          <w:lsdException w:name="Grid Table 2 Accent 4" w:uiPriority="47"/>
          <w:lsdException w:name="Grid Table 3 Accent 4" w:uiPriority="48"/>
          <w:lsdException w:name="Grid Table 4 Accent 4" w:uiPriority="49"/>
          <w:lsdException w:name="Grid Table 5 Dark Accent 4" w:uiPriority="50"/>
          <w:lsdException w:name="Grid Table 6 Colorful Accent 4" w:uiPriority="51"/>
          <w:lsdException w:name="Grid Table 7 Colorful Accent 4" w:uiPriority="52"/>
          <w:lsdException w:name="Grid Table 1 Light Accent 5" w:uiPriority="46"/>
          <w:lsdException w:name="Grid Table 2 Accent 5" w:uiPriority="47"/>
          <w:lsdException w:name="Grid Table 3 Accent 5" w:uiPriority="48"/>
          <w:lsdException w:name="Grid Table 4 Accent 5" w:uiPriority="49"/>
          <w:lsdException w:name="Grid Table 5 Dark Accent 5" w:uiPriority="50"/>
          <w:lsdException w:name="Grid Table 6 Colorful Accent 5" w:uiPriority="51"/>
          <w:lsdException w:name="Grid Table 7 Colorful Accent 5" w:uiPriority="52"/>
          <w:lsdException w:name="Grid Table 1 Light Accent 6" w:uiPriority="46"/>
          <w:lsdException w:name="Grid Table 2 Accent 6" w:uiPriority="47"/>
          <w:lsdException w:name="Grid Table 3 Accent 6" w:uiPriority="48"/>
          <w:lsdException w:name="Grid Table 4 Accent 6" w:uiPriority="49"/>
          <w:lsdException w:name="Grid Table 5 Dark Accent 6" w:uiPriority="50"/>
          <w:lsdException w:name="Grid Table 6 Colorful Accent 6" w:uiPriority="51"/>
          <w:lsdException w:name="Grid Table 7 Colorful Accent 6" w:uiPriority="52"/>
          <w:lsdException w:name="List Table 1 Light" w:uiPriority="46"/>
          <w:lsdException w:name="List Table 2" w:uiPriority="47"/>
          <w:lsdException w:name="List Table 3" w:uiPriority="48"/>
          <w:lsdException w:name="List Table 4" w:uiPriority="49"/>
          <w:lsdException w:name="List Table 5 Dark" w:uiPriority="50"/>
          <w:lsdException w:name="List Table 6 Colorful" w:uiPriority="51"/>
          <w:lsdException w:name="List Table 7 Colorful" w:uiPriority="52"/>
          <w:lsdException w:name="List Table 1 Light Accent 1" w:uiPriority="46"/>
          <w:lsdException w:name="List Table 2 Accent 1" w:uiPriority="47"/>
          <w:lsdException w:name="List Table 3 Accent 1" w:uiPriority="48"/>
          <w:lsdException w:name="List Table 4 Accent 1" w:uiPriority="49"/>
          <w:lsdException w:name="List Table 5 Dark Accent 1" w:uiPriority="50"/>
          <w:lsdException w:name="List Table 6 Colorful Accent 1" w:uiPriority="51"/>
          <w:lsdException w:name="List Table 7 Colorful Accent 1" w:uiPriority="52"/>
          <w:lsdException w:name="List Table 1 Light Accent 2" w:uiPriority="46"/>
          <w:lsdException w:name="List Table 2 Accent 2" w:uiPriority="47"/>
          <w:lsdException w:name="List Table 3 Accent 2" w:uiPriority="48"/>
          <w:lsdException w:name="List Table 4 Accent 2" w:uiPriority="49"/>
          <w:lsdException w:name="List Table 5 Dark Accent 2" w:uiPriority="50"/>
          <w:lsdException w:name="List Table 6 Colorful Accent 2" w:uiPriority="51"/>
          <w:lsdException w:name="List Table 7 Colorful Accent 2" w:uiPriority="52"/>
          <w:lsdException w:name="List Table 1 Light Accent 3" w:uiPriority="46"/>
          <w:lsdException w:name="List Table 2 Accent 3" w:uiPriority="47"/>
          <w:lsdException w:name="List Table 3 Accent 3" w:uiPriority="48"/>
          <w:lsdException w:name="List Table 4 Accent 3" w:uiPriority="49"/>
          <w:lsdException w:name="List Table 5 Dark Accent 3" w:uiPriority="50"/>
          <w:lsdException w:name="List Table 6 Colorful Accent 3" w:uiPriority="51"/>
          <w:lsdException w:name="List Table 7 Colorful Accent 3" w:uiPriority="52"/>
          <w:lsdException w:name="List Table 1 Light Accent 4" w:uiPriority="46"/>
          <w:lsdException w:name="List Table 2 Accent 4" w:uiPriority="47"/>
          <w:lsdException w:name="List Table 3 Accent 4" w:uiPriority="48"/>
          <w:lsdException w:name="List Table 4 Accent 4" w:uiPriority="49"/>
          <w:lsdException w:name="List Table 5 Dark Accent 4" w:uiPriority="50"/>
          <w:lsdException w:name="List Table 6 Colorful Accent 4" w:uiPriority="51"/>
          <w:lsdException w:name="List Table 7 Colorful Accent 4" w:uiPriority="52"/>
          <w:lsdException w:name="List Table 1 Light Accent 5" w:uiPriority="46"/>
          <w:lsdException w:name="List Table 2 Accent 5" w:uiPriority="47"/>
          <w:lsdException w:name="List Table 3 Accent 5" w:uiPriority="48"/>
          <w:lsdException w:name="List Table 4 Accent 5" w:uiPriority="49"/>
          <w:lsdException w:name="List Table 5 Dark Accent 5" w:uiPriority="50"/>
          <w:lsdException w:name="List Table 6 Colorful Accent 5" w:uiPriority="51"/>
          <w:lsdException w:name="List Table 7 Colorful Accent 5" w:uiPriority="52"/>
          <w:lsdException w:name="List Table 1 Light Accent 6" w:uiPriority="46"/>
          <w:lsdException w:name="List Table 2 Accent 6" w:uiPriority="47"/>
          <w:lsdException w:name="List Table 3 Accent 6" w:uiPriority="48"/>
          <w:lsdException w:name="List Table 4 Accent 6" w:uiPriority="49"/>
          <w:lsdException w:name="List Table 5 Dark Accent 6" w:uiPriority="50"/>
          <w:lsdException w:name="List Table 6 Colorful Accent 6" w:uiPriority="51"/>
          <w:lsdException w:name="List Table 7 Colorful Accent 6" w:uiPriority="52"/>
        </w:latentStyles>
        <w:style w:type="paragraph" w:default="1" w:styleId="a">
          <w:name w:val="Normal"/>
          <w:qFormat/>
          <w:pPr>
            <w:widowControl w:val="0"/>
            <w:jc w:val="both"/>
          </w:pPr>
        </w:style>
        <w:style w:type="character" w:default="1" w:styleId="a0">
          <w:name w:val="Default Paragraph Font"/>
          <w:uiPriority w:val="1"/>
          <w:semiHidden/>
          <w:unhideWhenUsed/>
        </w:style>
        <w:style w:type="table" w:default="1" w:styleId="a1">
          <w:name w:val="Normal Table"/>
          <w:uiPriority w:val="99"/>
          <w:semiHidden/>
          <w:unhideWhenUsed/>
          <w:tblPr>
            <w:tblInd w:w="0" w:type="dxa"/>
            <w:tblCellMar>
              <w:top w:w="0" w:type="dxa"/>
              <w:left w:w="108" w:type="dxa"/>
              <w:bottom w:w="0" w:type="dxa"/>
              <w:right w:w="108" w:type="dxa"/>
            </w:tblCellMar>
          </w:tblPr>
        </w:style>
        <w:style w:type="numbering" w:default="1" w:styleId="a2">
          <w:name w:val="No List"/>
          <w:uiPriority w:val="99"/>
          <w:semiHidden/>
          <w:unhideWhenUsed/>
        </w:style>
        <w:style w:type="table" w:styleId="a3">
          <w:name w:val="Table Grid"/>
          <w:basedOn w:val="a1"/>
          <w:uiPriority w:val="39"/>
          <w:rsid w:val="00313E87"/>
          <w:tblPr>
            <w:tblBorders>
              <w:top w:val="single" w:sz="4" w:space="0" w:color="auto"/>
              <w:left w:val="single" w:sz="4" w:space="0" w:color="auto"/>
              <w:bottom w:val="single" w:sz="4" w:space="0" w:color="auto"/>
              <w:right w:val="single" w:sz="4" w:space="0" w:color="auto"/>
              <w:insideH w:val="single" w:sz="4" w:space="0" w:color="auto"/>
              <w:insideV w:val="single" w:sz="4" w:space="0" w:color="auto"/>
            </w:tblBorders>
          </w:tblPr>
        </w:style>
        <w:style w:type="table" w:styleId="a4">
          <w:name w:val="Grid Table Light"/>
          <w:basedOn w:val="a1"/>
          <w:uiPriority w:val="40"/>
          <w:rsid w:val="00313E87"/>
          <w:tblPr>
            <w:tblBorders>
              <w:top w:val="single" w:sz="4" w:space="0" w:color="BFBFBF" w:themeColor="background1" w:themeShade="BF"/>
              <w:left w:val="single" w:sz="4" w:space="0" w:color="BFBFBF" w:themeColor="background1" w:themeShade="BF"/>
              <w:bottom w:val="single" w:sz="4" w:space="0" w:color="BFBFBF" w:themeColor="background1" w:themeShade="BF"/>
              <w:right w:val="single" w:sz="4" w:space="0" w:color="BFBFBF" w:themeColor="background1" w:themeShade="BF"/>
              <w:insideH w:val="single" w:sz="4" w:space="0" w:color="BFBFBF" w:themeColor="background1" w:themeShade="BF"/>
              <w:insideV w:val="single" w:sz="4" w:space="0" w:color="BFBFBF" w:themeColor="background1" w:themeShade="BF"/>
            </w:tblBorders>
          </w:tblPr>
        </w:style>
      </w:styles>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/docProps/core.xml" pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml" pkg:padding="256">
    <pkg:xmlData>
      <cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:dcmitype="http://purl.org/dc/dcmitype/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <dc:title/>
        <dc:subject/>
        <dc:creator>史晨阳</dc:creator>
        <cp:keywords/>
        <dc:description/>
        <cp:lastModifiedBy>史晨阳</cp:lastModifiedBy>
        <cp:revision>2</cp:revision>
        <dcterms:created xsi:type="dcterms:W3CDTF">2017-06-06T01:11:00Z</dcterms:created>
        <dcterms:modified xsi:type="dcterms:W3CDTF">2017-06-06T01:11:00Z</dcterms:modified>
      </cp:coreProperties>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/customXml/item1.xml" pkg:contentType="application/xml" pkg:padding="32">
    <pkg:xmlData>
      <b:Sources xmlns:b="http://schemas.openxmlformats.org/officeDocument/2006/bibliography" xmlns="http://schemas.openxmlformats.org/officeDocument/2006/bibliography" SelectedStyle="\APASixthEditionOfficeOnline.xsl" StyleName="APA" Version="6"></b:Sources>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/fontTable.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml">
    <pkg:xmlData>
      <w:fonts xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" mc:Ignorable="w14 w15">
        <w:font w:name="Calibri">
          <w:panose1 w:val="020F0502020204030204"/>
          <w:charset w:val="00"/>
          <w:family w:val="swiss"/>
          <w:pitch w:val="variable"/>
          <w:sig w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="宋体">
          <w:altName w:val="SimSun"/>
          <w:panose1 w:val="02010600030101010101"/>
          <w:charset w:val="86"/>
          <w:family w:val="auto"/>
          <w:pitch w:val="variable"/>
          <w:sig w:usb0="00000003" w:usb1="288F0000" w:usb2="00000016" w:usb3="00000000" w:csb0="00040001" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="Times New Roman">
          <w:panose1 w:val="02020603050405020304"/>
          <w:charset w:val="00"/>
          <w:family w:val="roman"/>
          <w:pitch w:val="variable"/>
          <w:sig w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="Calibri Light">
          <w:panose1 w:val="020F0302020204030204"/>
          <w:charset w:val="00"/>
          <w:family w:val="swiss"/>
          <w:pitch w:val="variable"/>
          <w:sig w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000"/>
        </w:font>
      </w:fonts>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/webSettings.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml">
    <pkg:xmlData>
      <w:webSettings xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" mc:Ignorable="w14 w15">
        <w:optimizeForBrowser/>
        <w:allowPNG/>
      </w:webSettings>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/docProps/app.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.extended-properties+xml" pkg:padding="256">
    <pkg:xmlData>
      <Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties" xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
        <Template>Normal</Template>
        <TotalTime>1</TotalTime>
        <Pages>1</Pages>
        <Words>13</Words>
        <Characters>75</Characters>
        <Application>Microsoft Office Word</Application>
        <DocSecurity>0</DocSecurity>
        <Lines>1</Lines>
        <Paragraphs>1</Paragraphs>
        <ScaleCrop>false</ScaleCrop>
        <Company/>
        <LinksUpToDate>false</LinksUpToDate>
        <CharactersWithSpaces>87</CharactersWithSpaces>
        <SharedDoc>false</SharedDoc>
        <HyperlinksChanged>false</HyperlinksChanged>
        <AppVersion>15.0000</AppVersion>
      </Properties>
    </pkg:xmlData>
  </pkg:part>
</pkg:package>
