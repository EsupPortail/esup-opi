<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:variable name="para-font-size">
		10pt
	</xsl:variable>
	<xsl:variable name="para-font-size-medium">
		8pt
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
						<xsl:apply-templates select="liste-prepa-pojo" />
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
				<fo:leader leader-pattern="space" leader-length="19cm" />
				Date :
				<xsl:value-of select="liste-prepa-pojo/date" />
			</fo:block>
			<fo:block font-size="{$para-font-size-title}" font-family="{$para-font-family}"
				text-align="start" font-weight="bold">
				<fo:leader leader-pattern="space" leader-length="24.6cm" />
				Page :
				<fo:page-number />
				/
				<fo:page-number-citation ref-id="theEnd" />
			</fo:block>
			<fo:block font-size="{$para-font-size-title}" font-family="{$para-font-family}"
				text-indent="0cm" text-align="center" font-weight="bold">
				EDITION PREPARATOIRE AUX COMMISSIONS PAR TITRE FONDANT LA DEMANDE
		</fo:block>
			<fo:block font-size="{$para-font-size-title}" font-family="{$para-font-family}"
				text-indent="0cm" text-align="center" font-weight="bold">
				Candidatures d�pos�es
				entre le 01/03/2012 et le <xsl:value-of select="liste-prepa-pojo/date" />
		</fo:block>
			<fo:block font-size="{$para-font-size-title}" font-family="{$para-font-family}"
				text-indent="0cm" text-align="start" font-weight="bold">
				<fo:leader />
			</fo:block>
			<fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
				text-indent="0cm" text-align="start" font-weight="bold">
				Commission :
				<xsl:value-of select="liste-prepa-pojo/commission/libelle" />
			</fo:block>
		</fo:block>
	</xsl:template>

	<xsl:template match="liste-prepa-pojo">
		<fo:block>
			<xsl:call-template name="miseEnPage" />
		</fo:block>
	</xsl:template>

	<xsl:template name="miseEnPage">
		<xsl:for-each select="map-ind-list">
			<fo:block font-size="{$para-font-size-small}" font-family="{$para-font-family}">
				<fo:table table-layout="fixed" width="100%" border-collapse="collapse">
					<fo:table-column column-width="proportional-column-width(0.3)" />
					<fo:table-column column-width="proportional-column-width(0.4)" />
					<fo:table-column column-width="proportional-column-width(1)" />
					<fo:table-column column-width="proportional-column-width(0.75)" />
					<fo:table-column column-width="proportional-column-width(0.4)" />
					<fo:table-column column-width="proportional-column-width(0.5)" />
					<fo:table-column column-width="proportional-column-width(1)" />
					<fo:table-column column-width="proportional-column-width(0.2)" />
					<fo:table-column column-width="proportional-column-width(0.4)" />
					<fo:table-column column-width="proportional-column-width(2)" />
					<fo:table-header color="black"  border="1pt solid black">
						<fo:table-row>
							<fo:table-cell padding="2pt" border="0pt solid black" 
								number-columns-spanned="10" font-weight="bold">
								<fo:block font-size="{$para-font-size-medium}" >
									Titre fondant la demande :
									<xsl:value-of select="key/lib-dac" /><xsl:value-of select="key/lib-etape" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row background-color="{$grisClair}" font-weight="bold">
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Cmi</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Dossier</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Nom Pr�nom</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Bac</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Typ. Doss.</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Code</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Etape</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Avis</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Prop. comm.</fo:block>
							</fo:table-cell>
							<fo:table-cell padding="2pt" border="0pt solid black">
								<fo:block>Commentaire</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-header>
					<fo:table-body border-left="1pt solid black" border-right="1pt solid black"
									border-bottom="0.5pt solid black" border-top="0.5pt solid black"> 
						<xsl:for-each select="value">
							<fo:table-row border="1" >
								<fo:table-cell padding="2pt" border-bottom="0.5pt solid black" border-top="0.5pt solid black">
									<fo:block>
										<xsl:value-of select="code-cmi" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell padding="2pt" border-bottom="0.5pt solid black" border-top="0.5pt solid black">
									<fo:block>
										<xsl:value-of select="num-dossier-opi" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell padding="2pt" border-bottom="0.5pt solid black" border-top="0.5pt solid black">
									<fo:block>
										<xsl:value-of select="nom" />
										<xsl:text> </xsl:text>
										<xsl:value-of select="prenom" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell padding="2pt" border-bottom="0.5pt solid black" border-top="0.5pt solid black">
									<fo:block>
										<xsl:value-of select="bac" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell number-columns-spanned="8" padding="0pt" border-bottom="0.5pt solid black" border-top="0.5pt solid black">
									<fo:block>
										<fo:table table-layout="fixed" width="100%"
											border-collapse="collapse">
											<fo:table-column column-width="proportional-column-width(0.4)" />
											<fo:table-column column-width="proportional-column-width(0.5)" />
											<fo:table-column column-width="proportional-column-width(1)" />
											<fo:table-column column-width="proportional-column-width(0.2)" />
											<fo:table-column column-width="proportional-column-width(0.4)" />
											<fo:table-column column-width="proportional-column-width(2)" />
											<fo:table-body>
												<xsl:for-each select="ind-voeux-pojo">
													<fo:table-row>
														<fo:table-cell padding="2pt" border="0pt solid black">
															<fo:block>
																<xsl:value-of select="ind-voeu/cod-type-trait" />
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding="2pt" border="0pt solid black">
															<fo:block>
																<xsl:value-of select="vrs-etape/cod-etp" />
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding="2pt" border="0pt solid black">
															<fo:block>
																<xsl:value-of select="vrs-etape/lic-etp" />
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding="2pt" border="0pt solid black">
															<fo:block>
																<xsl:value-of select="avis-en-service/result/code" />
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding="2pt" border="0pt solid black">
															<fo:block>
																<xsl:if test="ind-voeu/is-prop = 'true'">Oui</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding="2pt" border="0pt solid black">
															<fo:block>
																<xsl:value-of select="avis-en-service/motivation-avis/libelle"/>
																<xsl:if test="avis-en-service/motivation-avis 
																			and avis-en-service/commentaire">
																	<xsl:text> / </xsl:text>
																</xsl:if>
																<xsl:value-of select="avis-en-service/commentaire"/>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:if test="position() = last()">
								<fo:table-row>
									<fo:table-cell padding="2pt" border-bottom="0.5pt solid black"
										border-top="0.5pt solid black" number-columns-spanned="10"
										text-align="right" font-weight="bold">
										<fo:block>
											Nombre de dossiers pour ce titre : <xsl:value-of select="position()" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</xsl:if>	
						</xsl:for-each>
					</fo:table-body>
				</fo:table>
			</fo:block>
			<xsl:if test="position() != last()">
				<fo:block break-after='page'/>
			</xsl:if>
		</xsl:for-each>
		<fo:block id="theEnd" />
	</xsl:template>




</xsl:stylesheet>