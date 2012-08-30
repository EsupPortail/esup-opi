<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:variable name="para-font-size">
		11pt
	</xsl:variable>
	<xsl:variable name="para-font-size-small">
		9pt
	</xsl:variable>
	<xsl:variable name="para-font-family">
		Arial Narrow,sans-serif
	</xsl:variable>

	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			xmlns:fox="http://xml.apache.org/fop/extensions">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="all"
					page-height="29.699cm" page-width="20.999cm" margin-right="1cm"
					margin-left="1cm" margin-bottom="1cm" margin-top="1cm">
					<fo:region-body margin-top="0cm" margin-bottom="1cm" />
					<fo:region-before extent="1.5cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="all">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block>
						<fo:block>
							<fo:external-graphic src="url('logo-header-rennes1.jpg')"
								width="3cm" height="1.5cm" />
						</fo:block>
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:apply-templates select="array-list" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>


	<xsl:template match="array-list">
		<fo:block>
			<xsl:call-template name="miseEnPage" />
		</fo:block>
	</xsl:template>


	<xsl:template name="miseEnPage">
		<fo:block font-family="{$para-font-family}">
			<xsl:call-template name="entete" />
		</fo:block>
		<fo:block font-family="{$para-font-family}">
			<xsl:call-template name="members" />
		</fo:block>
		<fo:block font-family="{$para-font-family}">
			<xsl:call-template name="signature" />
		</fo:block>
	</xsl:template>

	<xsl:template name="entete">
		<fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				LE PRESIDENT DE L'UNIVERSITE DE RENNES 1
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				Vu
				<fo:leader leader-pattern="space" leader-length="1cm" />
				le code de l'éducation
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="12pt" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:inline font-size="{$para-font-size}" font-style="italic"
					font-weight="bold" font-family="{$para-font-family}">
					Vu
					<fo:leader leader-pattern="space" leader-length="1cm" />
					le décret n°85-906 du 23 août 1985 fixant les
				</fo:inline>
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="7.55cm" text-indent="0cm" font-style="italic"
				font-weight="bold">
				<fo:inline font-size="{$para-font-size}" font-style="italic"
					font-weight="bold" font-family="{$para-font-family}">
					conditions de validation des études,
					expériences
				</fo:inline>
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="7.55cm" text-indent="0cm" font-style="italic"
				font-weight="bold">
				<fo:inline font-size="{$para-font-size}" font-style="italic"
					font-weight="bold" font-family="{$para-font-family}">
					professionnelles ou acquis
					personnels en vue de
				</fo:inline>
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="7.55cm" text-indent="0cm" font-style="italic"
				font-weight="bold">
				<fo:inline font-size="{$para-font-size}" font-style="italic"
					font-weight="bold" font-family="{$para-font-family}">
					l'accès aux différents niveaux de
					l'enseignement supérieur,
				</fo:inline>
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-style="italic" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="12pt" font-family="'{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:inline font-size="{$para-font-size}" font-style="italic"
					font-weight="bold" font-family="{$para-font-family}">
					Vu
					<fo:leader leader-pattern="space" leader-length="1cm" />
					le décret n°2002-481 du 8 avril 2002 relatif aux grades
				</fo:inline>
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="7.55cm" text-indent="0cm" font-style="italic"
				font-weight="bold">
				<fo:inline font-size="{$para-font-size}" font-style="italic"
					font-weight="bold" font-family="{$para-font-family}">
					et titres universitaires et aux
					diplômes nationaux,
				</fo:inline>
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6.35cm" text-indent="0cm" font-style="italic"
				font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="18pt" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm" text-align="center" font-style="italic"
				font-weight="bold">
				ARRETE
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm" font-style="italic">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm" font-style="italic">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="1cm" text-indent="0cm" font-style="italic" font-weight="bold"
				text-decoration="underline">
				Article unique 
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="1cm" text-indent="0cm">
				La commission pédagogique
				« 
				<xsl:value-of select="commission/libelle" />
				»
				chargée d'émettre la
				proposition d'accès aux différents niveaux de
				l'enseignement
				supérieur est composée de la manière suivante :
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
		</fo:block>

	</xsl:template>

	<xsl:template name="members">
		<fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="0cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
		</fo:block>
		<fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="1cm" text-indent="0cm" text-decoration="underline">
				Membres :
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}">
				<fo:table table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(1)" />
					<fo:table-column column-width="proportional-column-width(0.5)" />
					<fo:table-body>
						<xsl:for-each select="commission/members">
							<fo:table-row>
								<fo:table-cell padding-left="4cm">
									<fo:block text-align="left">
										<xsl:value-of select="nom" />
										<xsl:text> </xsl:text>
										<xsl:value-of select="prenom" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<xsl:value-of select="type" />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:for-each>
					</fo:table-body>
				</fo:table>
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
		</fo:block>
	</xsl:template>
	<xsl:template name="signature">
		<fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-weight="bold" text-align="center">
				Fait à Rennes, le
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-weight="bold" text-align="center">
				Le Président de l'Université
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				margin-left="6cm" text-indent="0cm" font-weight="bold" text-align="center">
				G. CATHELINEAU
			</fo:block>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>