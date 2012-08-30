<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:variable name="para-font-size">
		10pt
	</xsl:variable>
	<xsl:variable name="para-font-size-small">
		7pt
	</xsl:variable>
	<xsl:variable name="para-font-size-title">
		12pt
	</xsl:variable>
	<xsl:variable name="padding-cell-tab-info">
		3pt
	</xsl:variable>
	<xsl:variable name="para-font-family">
		Arial Narrow,sans-serif
	</xsl:variable>
	<xsl:variable name="para-font-family-small">
		Trebuchet MS,sans-serif
	</xsl:variable>

	<xsl:variable name="grisClair">
		#e6e6e6
	</xsl:variable>


	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			xmlns:fox="http://xml.apache.org/fop/extensions">
			<fo:layout-master-set>

				<fo:simple-page-master master-name="all"
					page-height="20.999cm" page-width="29.699cm" margin-right="0.5cm"
					margin-left="0.5cm" margin-bottom="1cm" margin-top="0.5cm">
					<fo:region-body margin-top="3cm" margin-bottom="1cm" />
					<fo:region-before extent="3cm" region-name="xsl-region-before" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="all">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block>
						<xsl:call-template name="entete" />
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:apply-templates select="liste-stat-pojo" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template name="entete">
		<fo:block>
			<fo:block font-size="{$para-font-size-title}" font-family="{$para-font-family}"
				text-indent="0cm" text-align="start" font-weight="bold">
				UNIVERSITE DE RENNES 1
				<fo:leader leader-pattern="space" leader-length="18cm" />
				Date :
				<xsl:value-of select="liste-stat-pojo/date" />
			</fo:block>
			<fo:block font-size="{$para-font-size-title}" font-family="{$para-font-family}"
				text-align="start" font-weight="bold">
				<fo:leader leader-pattern="space" leader-length="24.9cm" />
				Page :
				<fo:page-number />
				/
				<fo:page-number-citation ref-id="theEnd" />
			</fo:block>
			<fo:block font-size="{$para-font-size-title}" font-family="{$para-font-family}"
				text-indent="0cm" text-align="start" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				text-indent="0cm" text-align="start" font-weight="bold">
				<xsl:value-of select="liste-stat-pojo/titre" />
			</fo:block>
		</fo:block>
	</xsl:template>

	<xsl:template match="liste-stat-pojo">
		<fo:block>
			<xsl:call-template name="miseEnPage" />
		</fo:block>
	</xsl:template>

	<xsl:template name="miseEnPage">
		<fo:block font-size="{$para-font-size-small}" font-family="{$para-font-family}">
			<fo:table table-layout="fixed" width="100%" border-collapse="collapse">
				<xsl:for-each select="lib-camp">
					<fo:table-column column-width="proportional-column-width(1)"/>
				</xsl:for-each>
				<fo:table-header color="black" border-top="0.5pt solid black" border-left="0.5pt solid black"
					border-right="0.5pt solid black" background-color="{$grisClair}" font-weight="bold">
					<fo:table-row>
						<xsl:variable name="nombre-abscisse">
							<xsl:value-of select="nb-abscisse"/>
						</xsl:variable>
						<xsl:for-each select="lib-camp">
							<xsl:variable name="libele-campagne">
								<xsl:value-of select="."/>
							</xsl:variable>
							<xsl:choose>
								<xsl:when test="$libele-campagne=''">
									<fo:table-cell padding="2pt">
										<fo:block></fo:block>
									</fo:table-cell>
								</xsl:when>
								<xsl:otherwise>
									<fo:table-cell padding="2pt" border-left="0.5pt solid black">
										<fo:block><xsl:value-of select="."/></fo:block>
									</fo:table-cell>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</fo:table-row>
					<fo:table-row>
						<xsl:for-each select="titre-abscisse">
							<fo:table-cell padding="2pt" border-left="0.5pt solid black" border-top="0.5pt solid black">
								<fo:block><xsl:value-of select="."/></fo:block>
							</fo:table-cell>
						</xsl:for-each>
					</fo:table-row>
				</fo:table-header>
				<fo:table-body>
					<xsl:for-each select="map-stat-list">
						<fo:table-row>
							<fo:table-cell padding="2pt"  color="black" border="0.5pt solid black"
								background-color="{$grisClair}" font-weight="bold">
								<fo:block><xsl:value-of select="key"/></fo:block>
							</fo:table-cell>
							<xsl:for-each select="value">
								<fo:table-cell padding="2pt" border="0.5pt solid black">
									<fo:block><xsl:value-of select="."/></fo:block>
								</fo:table-cell>
							</xsl:for-each>
						</fo:table-row>
					</xsl:for-each>
				</fo:table-body>
		    </fo:table>
		</fo:block>
	</xsl:template>

</xsl:stylesheet>