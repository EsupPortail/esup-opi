<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="para-font-size">
        10pt
    </xsl:variable>
    <xsl:variable name="para-font-size-small">
        8pt
    </xsl:variable>
    <xsl:variable name="para-font-size-title">
        13pt
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
                >
            <fo:layout-master-set>
                <fo:simple-page-master master-name="all"
                                       page-height="29.699cm" page-width="20.999cm" margin-right="1cm"
                                       margin-left="1cm" margin-bottom="1cm" margin-top="1.10cm">
                    <fo:region-body margin-top="0cm" margin-bottom="1cm"/>
                    <fo:region-before extent="0cm"/>
                    <fo:region-after extent="0.5cm" region-name="xsl-region-after"/>
                </fo:simple-page-master>
                <fo:simple-page-master master-name="infoForIndividu"
                                       border="" page-height="29.699cm" page-width="20.999cm"
                                       margin-right="1cm" margin-left="1cm" margin-bottom="1cm"
                                       margin-top="1.10cm">
                    <fo:region-body margin-top="0cm" margin-bottom="0cm"/>
                    <fo:region-before extent="0cm"/>
                    <fo:region-after extent="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="all">
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block>
                        <fo:block line-height="110%" hyphenate="false"
                                  font-family="{$para-font-family-small}" text-align="end">
                            <fo:inline color="#333333" font-size="9pt" font-weight="bold">
                                Dossier de candidature à renvoyer : page
                                <fo:page-number/>
                            </fo:inline>
                        </fo:block>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <xsl:apply-templates select="ind-document"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
            <fo:page-sequence master-reference="infoForIndividu">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <xsl:call-template name="infoForIndividu"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="ind-document">
        <fo:block>
            <xsl:call-template name="miseEnPage"/>
        </fo:block>
    </xsl:template>


    <xsl:template name="miseEnPage">
        <fo:block font-family="{$para-font-family}">
            <xsl:call-template name="entete"/>
        </fo:block>
        <xsl:call-template name="blocks"/>
    </xsl:template>

    <xsl:template name="entete">
        <fo:block>
            <fo:table table-layout="fixed" width="100%">
                <fo:table-column column-width="proportional-column-width(0.5)"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="4cm"/>
                <fo:table-body>
                    <fo:table-row height="1.5cm">
                        <fo:table-cell width="3cm">
                            <fo:block>
                                <fo:external-graphic src="url('logo-header-rennes1.jpg')"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell/>
                        <fo:table-cell border="1px solid black"
                                       number-rows-spanned="2">
                            <fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="18pt" font-family="{$para-font-family}"
                                          text-align="center" color="#333333">
                                    <fo:leader/>
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="11pt" font-family="{$para-font-family}"
                                          text-align="center" color="#808080" font-weight="bold">Photographie
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="11pt" font-family="{$para-font-family}"
                                          text-align="center" color="#808080" font-weight="bold">d'identité
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="11pt" font-family="{$para-font-family}"
                                          text-align="center" color="#808080" font-weight="bold">à
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="11pt" font-family="{$para-font-family}"
                                          text-align="center" color="#808080" font-weight="bold">agrafer
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="4pt" font-family="{$para-font-family}"
                                          text-align="center" color="#808080" font-weight="bold">
                                    <fo:leader/>
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="{$para-font-size-small}"
                                          font-family="{$para-font-family}" text-align="center" color="#808080"
                                          font-weight="bold">format
                                    4x4
                                </fo:block>
                            </fo:block>

                        </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row height="2.5cm">
                        <fo:table-cell>
                            <fo:block>
                                <fo:block>
                                    <fo:block line-height="110%" hyphenate="false"
                                              language="fr" country="FR" font-size="11pt"
                                              font-family="{$para-font-family-small}">
                                        <fo:inline font-size="{$para-font-size-small}"
                                                   font-weight="bold" font-family="{$para-font-family}">
                                            <xsl:value-of
                                                    select="cmi-and-vows-ind/key/contact-commission/corresponding"/>
                                        </fo:inline>
                                    </fo:block>
                                    <fo:block line-height="110%" hyphenate="false"
                                              language="fr" country="FR" font-size="{$para-font-size-small}"
                                              font-family="{$para-font-family}" font-weight="bold">
                                        <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/adresse/adr1"/>
                                        <xsl:if test="cmi-and-vows-ind/key/adresse-pojo/adresse/adr2 != ''">
                                            -
                                            <xsl:value-of
                                                    select="cmi-and-vows-ind/key/adresse-pojo/adresse/adr2"/>
                                        </xsl:if>
                                        <xsl:if test="cmi-and-vows-ind/key/adresse-pojo/adresse/adr3 != ''">
                                            <fo:block>
                                                <xsl:value-of
                                                        select="cmi-and-vows-ind/key/adresse-pojo/adresse/adr3"/>
                                            </fo:block>
                                        </xsl:if>
                                    </fo:block>
                                    <fo:block font-size="{$para-font-size-small}"
                                              font-family="{$para-font-family}" font-weight="bold">
                                        <xsl:value-of
                                                select="cmi-and-vows-ind/key/adresse-pojo/adresse/cod-bdi"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of
                                                select="cmi-and-vows-ind/key/adresse-pojo/commune/lib-commune"/>
                                        <xsl:if test="cmi-and-vows-ind/key/adresse-pojo/is-cedex = 'true'">
                                            <xsl:text> </xsl:text>
                                            CEDEX
                                            <xsl:value-of
                                                    select="cmi-and-vows-ind/key/adresse-pojo/adresse/cedex"/>
                                        </xsl:if>
                                    </fo:block>
                                    <fo:block line-height="110%" hyphenate="false"
                                              language="fr" country="FR" font-size="{$para-font-size-small}"
                                              font-family="{$para-font-family}" font-weight="bold">
                                        <xsl:if
                                                test="cmi-and-vows-ind/key/adresse-pojo/adresse/phone-number != ''">
                                            Tél :
                                            <xsl:value-of
                                                    select="cmi-and-vows-ind/key/adresse-pojo/adresse/phone-number"/>
                                        </xsl:if>
                                        <xsl:if
                                                test="cmi-and-vows-ind/key/adresse-pojo/adresse/fax-number != ''">
                                            - Fax :
                                            <xsl:value-of
                                                    select="cmi-and-vows-ind/key/adresse-pojo/adresse/fax-number"/>
                                        </xsl:if>
                                    </fo:block>
                                </fo:block>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell>
                            <fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="11pt"
                                          font-family="{$para-font-family-small}" text-align="center">
                                    <fo:inline font-size="{$para-font-size-title}"
                                               font-weight="bold" font-family="{$para-font-family}">
                                        DOSSIER DE CANDIDATURE
                                        <xsl:value-of select="annee-universitaire"/>
                                    </fo:inline>
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="11pt"
                                          font-family="{$para-font-family-small}" text-align="center">
                                    <fo:inline font-size="{$para-font-size-title}"
                                               font-weight="bold" font-family="{$para-font-family}">
                                        Formation Continue
                                    </fo:inline>
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="4pt" font-family="{$para-font-family}"
                                          text-align="center" font-weight="bold">
                                    <fo:leader/>
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="{$para-font-size}"
                                          font-family="{$para-font-family-small}" text-align="center">
                                    <fo:inline font-size="{$para-font-size}" font-weight="bold"
                                               font-family="{$para-font-family}">Dossier n° :
                                    </fo:inline>
                                    <fo:inline font-size="{$para-font-size-title}"
                                               font-weight="bold" font-family="{$para-font-family}">
                                        <xsl:value-of select="individu-pojo/individu/num-dossier-opi"/>
                                    </fo:inline>
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="{$para-font-size}"
                                          font-family="{$para-font-family-small}" text-align="center">
                                    <fo:inline font-size="{$para-font-size}" font-weight="bold"
                                               font-family="{$para-font-family}">Commission :
                                    </fo:inline>
                                    <fo:inline font-size="{$para-font-size}" font-weight="bold"
                                               font-family="{$para-font-family}">
                                        <xsl:value-of select="cmi-and-vows-ind/key/commission/libelle"/>
                                    </fo:inline>
                                </fo:block>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>

    </xsl:template>

    <xsl:template name="blocks">
        <fo:block padding-bottom="15px">
            <xsl:call-template name="candidatures"/>
        </fo:block>
        <fo:block padding-bottom="15px">
            <xsl:call-template name="coordonnee"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="situation"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="cursusPro"/>
        </fo:block>
        <fo:block padding-bottom="15px" font-family="{$para-font-family}"
                  font-size="{$para-font-size}">
            <xsl:call-template name="bacEqu"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="cursusScol"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="formationProf"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="informationCompl"/>
        </fo:block>
        <fo:block break-after="page"/>
        <fo:block padding-bottom="25pt">
            <xsl:call-template name="entetePage2"/>
        </fo:block>
        <fo:block line-height="110%" font-weight="bold"
                  font-size="{$para-font-size-title}" padding-bottom="25pt"
                  font-family="{$para-font-family-small}" text-align="center">
            CONSTITUTION DE
            VOTRE DOSSIER
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="pieceJustificative"/>
        </fo:block>
        <fo:block padding-bottom="25pt">
            <xsl:call-template name="signature"/>
        </fo:block>
        <fo:block break-after="page"/>
        <fo:block padding-bottom="25pt">
            <xsl:call-template name="entetePage2"/>
        </fo:block>
        <fo:block>
            <xsl:for-each select="cmi-and-vows-ind/value">
                <fo:block line-height="110%" font-size="11pt"
                          font-family="{$para-font-family-small}" padding-top="25pt"
                          absolute-position="fixed">
                    <fo:inline color="#333333" font-weight="bold"
                               font-family="{$para-font-family}">
                        <xsl:value-of select="lib-web-vet"/>
                    </fo:inline>
                </fo:block>
                <xsl:call-template name="cadreAdmin"/>
            </xsl:for-each>
        </fo:block>
        <!--
            <fo:block padding-top="15pt"> <xsl:call-template name="calendrier" />
            </fo:block>
        -->
    </xsl:template>

    <xsl:template name="candidatures">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">CANDIDATURES 
            </fo:inline>
            <fo:inline color="#333333" font-size="10pt" font-style="italic"
                       font-weight="bold" font-family="{$para-font-family}">(ordre
                de préférence à préciser)
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
            <fo:table table-layout="fixed" width="100%" border="1px solid black">
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="proportional-column-width(0.3)"/>
                <fo:table-body>
                    <xsl:for-each select="cmi-and-vows-ind/value">
                        <fo:table-row>
                            <fo:table-cell padding="{$padding-cell-tab-info}">
                                <fo:block>
                                    <fo:inline background-color="#ffff00" font-size="{$para-font-size}"
                                               font-family="{$para-font-family}">
                                        <xsl:value-of select="lib-web-vet"/>
                                    </fo:inline>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell padding="{$padding-cell-tab-info}">
                                <fo:block text-align="right">
                                    choix n° : ___
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </xsl:for-each>
                </fo:table-body>
            </fo:table>

        </fo:block>
    </xsl:template>


    <xsl:template name="coordonnee">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">COORDONNEES PERSONNELLES 
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
            <xsl:call-template name="etatCivil"/>
        </fo:block>
    </xsl:template>

    <xsl:template name="etatCivil">
        <fo:table table-layout="fixed" width="100%" border="1px solid black">
            <fo:table-column column-width="proportional-column-width(0.7)"/>
            <fo:table-column column-width="proportional-column-width(0.8)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Civilité :
                            <xsl:if test="individu-pojo/individu/sexe = 'M'">
                                Monsieur
                            </xsl:if>
                            <xsl:if test="individu-pojo/individu/sexe = 'F'">
                                Madame
                            </xsl:if>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            <xsl:if test="individu-pojo/individu/code-etu">

                                Code Etudiant :
                                <xsl:value-of select="individu-pojo/individu/code-etu"/>

                            </xsl:if>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}">
                        <fo:block font-size="{$para-font-size}">
                            Nom :
                            <fo:inline font-size="{$para-font-size-title}"
                                       font-weight="bold" font-family="{$para-font-family}">
                                <xsl:value-of select="individu-pojo/individu/nom-patronymique"/>
                            </fo:inline>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Nom d'usage :
                            <fo:inline font-size="{$para-font-size-title}"
                                       font-weight="bold" font-family="{$para-font-family}">
                                <xsl:value-of select="individu-pojo/individu/nom-usuel"/>
                            </fo:inline>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Prénom :
                            <fo:inline font-size="{$para-font-size-title}"
                                       font-weight="bold" font-family="{$para-font-family}">
                                <xsl:value-of select="individu-pojo/individu/prenom"/>
                            </fo:inline>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}">
                        <fo:block font-size="{$para-font-size}">
                            Né(e) le :
                            <xsl:value-of select="individu-pojo/individu/date-naissance"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}">
                        <fo:block font-size="{$para-font-size}">
                            à :
                            <xsl:value-of select="individu-pojo/individu/ville-naissance"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}">
                        <fo:block font-size="{$para-font-size}">
                            Nationalité :
                            <xsl:value-of select="individu-pojo/nationalite/lib-nat"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2">
                        <fo:block font-size="{$para-font-size}">
                            <fo:table table-layout="fixed" width="100%">
                                <fo:table-column column-width="50pt"/>
                                <fo:table-column column-width="proportional-column-width(1)"/>
                                <fo:table-body>
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block>
                                                Adresse :
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding-left="0pt">
                                            <fo:block>
                                                <xsl:value-of select="adresse-pojo/adresse/adr1"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <xsl:if test="adresse-pojo/adresse/adr2 != ''">
                                        <fo:table-row>
                                            <fo:table-cell/>
                                            <fo:table-cell padding-left="0pt">
                                                <fo:block>
                                                    <xsl:value-of select="adresse-pojo/adresse/adr2"/>
                                                    <xsl:if test="adresse-pojo/adresse/adr3 != ''">
                                                        -
                                                        <xsl:value-of select="adresse-pojo/adresse/adr3"/>
                                                    </xsl:if>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:if>
                                    <fo:table-row>
                                        <fo:table-cell/>
                                        <fo:table-cell padding-left="0pt">
                                            <fo:block>
                                                <xsl:value-of select="adresse-pojo/adresse/cod-bdi"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="adresse-pojo/commune/lib-commune"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell/>
                                        <fo:table-cell padding-left="0pt">
                                            <fo:block>
                                                Pays -
                                                <xsl:value-of select="adresse-pojo/pays/lib-pay"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-right="0pt">
                        <fo:block font-size="{$para-font-size}">
                            <fo:block>
                                Tél. portable :
                                <xsl:value-of select="individu-pojo/individu/numero-tel-portable"/>
                            </fo:block>
                            <fo:block>
                                Tél. fixe :
                                <xsl:value-of select="adresse-pojo/adresse/phone-number"/>
                            </fo:block>
                            <fo:block>
                                Courriel :
                                <xsl:value-of select="individu-pojo/individu/adress-mail"/>
                            </fo:block>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>

    </xsl:template>

    <xsl:template name="situation">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">SITUATION ACTUELLE 
            </fo:inline>
        </fo:block>
        <xsl:choose>
            <xsl:when test="ind-situation/type = 'sal'">
                <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
                    <xsl:call-template name="situationSal"/>
                </fo:block>
            </xsl:when>
            <xsl:when test="ind-situation/type = 'pro_lib'">
                <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
                    <xsl:call-template name="situationProLib"/>
                </fo:block>
            </xsl:when>
            <xsl:when test="ind-situation/type = 'dem_emp'">
                <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
                    <xsl:call-template name="situationDemEmp"/>
                </fo:block>
            </xsl:when>
            <xsl:when test="ind-situation/type = 'etu'">
                <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
                    <xsl:call-template name="situationEtu"/>
                </fo:block>
            </xsl:when>
            <xsl:when test="ind-situation/type = 'other'">
                <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
                    <xsl:call-template name="situationAutre"/>
                </fo:block>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="situationSal">
        <fo:table table-layout="fixed" width="100%" border="1px solid black">
            <fo:table-column column-width="proportional-column-width(0.7)"/>
            <fo:table-column column-width="proportional-column-width(0.8)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Salarié(e)
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Type de contrat :
                            <xsl:choose>
                                <xsl:when test="ind-situation/code-type-contrat = 'CDD'">
                                    CDD (jusqu'au :
                                    <xsl:value-of select="ind-situation/date-fin-cDD"/>
                                    )
                                </xsl:when>
                                <xsl:when test="ind-situation/code-type-contrat = 'CDI'">
                                    CDI
                                </xsl:when>
                                <xsl:when test="ind-situation/code-type-contrat = 'autre'">
                                    Autre :
                                    <xsl:value-of select="ind-situation/autre-type-contrat"/>
                                </xsl:when>
                            </xsl:choose>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2">
                        <fo:block font-size="{$para-font-size}">
                            Statut :
                            <xsl:choose>
                                <xsl:when test="ind-situation/code-type-statut = 'cadre'">
                                    cadre
                                </xsl:when>
                                <xsl:when test="ind-situation/code-type-statut = 'employe'">
                                    employé(e)
                                </xsl:when>
                                <xsl:when test="ind-situation/code-type-statut = 'ouvrier'">
                                    ouvrier
                                </xsl:when>
                                <xsl:when test="ind-situation/code-type-statut = 'proInterm'">
                                    profession intermédiaire
                                </xsl:when>
                            </xsl:choose>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            <xsl:if test="ind-situation/grade">

                                Grade :
                                <xsl:value-of select="ind-situation/grade"/>

                            </xsl:if>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3">
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2">
                        <fo:block font-size="{$para-font-size}">
                            Employeur :
                            <xsl:value-of select="ind-situation/raison-sociale"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}">
                        <fo:block font-size="{$para-font-size}">
                            Organisme :
                            <xsl:choose>
                                <xsl:when test="ind-situation/code-type-orga = 'public'">
                                    public ou parapublic
                                </xsl:when>
                                <xsl:when test="ind-situation/code-type-orga = 'private'">
                                    privé
                                </xsl:when>
                            </xsl:choose>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2">
                        <fo:block font-size="{$para-font-size}">
                            Secteur d'activité :
                            <xsl:value-of select="ind-situation/secteur-activity"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2">
                        <fo:block font-size="{$para-font-size}">
                            <fo:table table-layout="fixed" width="100%">
                                <fo:table-column column-width="50pt"/>
                                <fo:table-column column-width="proportional-column-width(1)"/>
                                <fo:table-body>
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block>
                                                Adresse :
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding-left="0pt">
                                            <fo:block>
                                                <xsl:value-of select="adresse-employeur-pojo/adresse/adr1"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <xsl:if test="adresse-employeur-pojo/adresse/adr2 != ''">
                                        <fo:table-row>
                                            <fo:table-cell/>
                                            <fo:table-cell padding-left="0pt">
                                                <fo:block>
                                                    <xsl:value-of select="adresse-employeur-pojo/adresse/adr2"/>
                                                    <xsl:if test="adresse-employeur-pojo/adresse/adr3 != ''">
                                                        -
                                                        <xsl:value-of select="adresse-employeur-pojo/adresse/adr3"/>
                                                    </xsl:if>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:if>
                                    <fo:table-row>
                                        <fo:table-cell/>
                                        <fo:table-cell padding-left="0pt">
                                            <fo:block>
                                                <xsl:value-of select="adresse-employeur-pojo/adresse/cod-bdi"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="adresse-employeur-pojo/commune/lib-commune"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell/>
                                        <fo:table-cell padding-left="0pt">
                                            <fo:block>
                                                Pays -
                                                <xsl:value-of select="adresse-employeur-pojo/pays/lib-pay"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-right="0pt">
                        <fo:block font-size="{$para-font-size}">
                            <fo:block>
                                Tél. :
                                <xsl:value-of select="adresse-employeur-pojo/adresse/phone-number"/>
                            </fo:block>
                            <fo:block>
                                Courriel :
                                <xsl:value-of select="adresse-employeur-pojo/adresse/mail"/>
                            </fo:block>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <xsl:call-template name="blockHandicap"/>
            </fo:table-body>
        </fo:table>

    </xsl:template>

    <xsl:template name="situationProLib">
        <fo:table table-layout="fixed" width="100%" border="1px solid black">
            <fo:table-column column-width="proportional-column-width(0.7)"/>
            <fo:table-column column-width="proportional-column-width(0.8)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Professionel libéral
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Statut précis :
                            <xsl:value-of select="ind-situation/exact-statut"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="2"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Activité :
                            <xsl:value-of select="ind-situation/activity"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Date de début d'activité :
                            <xsl:value-of select="ind-situation/date-start-activity"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <xsl:call-template name="blockHandicap"/>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <xsl:template name="situationDemEmp">
        <fo:table table-layout="fixed" width="100%" border="1px solid black">
            <fo:table-column column-width="proportional-column-width(0.7)"/>
            <fo:table-column column-width="proportional-column-width(0.8)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Demandeur d'emploi
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Date d'inscription au Pôle Emploi :
                            <xsl:value-of select="ind-situation/date-inscription-pE"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Indemnisé :
                            <xsl:if test="ind-situation/compensated = 'true'">
                                <fo:external-graphic src="url('squareChecked.JPG')"/>
                            </xsl:if>
                            <xsl:if test="ind-situation/compensated = 'false'">
                                <fo:external-graphic src="url('square.JPG')"/>
                            </xsl:if>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Date de début de l'indemnisation :
                            <xsl:value-of select="ind-situation/date-start-compensation"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Date de fin de l'indemnisation :
                            <xsl:value-of select="ind-situation/date-end-compensation"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <xsl:call-template name="blockHandicap"/>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <xsl:template name="situationEtu">
        <fo:table table-layout="fixed" width="100%" border="1px solid black">
            <fo:table-column column-width="proportional-column-width(0.7)"/>
            <fo:table-column column-width="proportional-column-width(0.8)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Etudiant(e)
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <xsl:call-template name="blockHandicap"/>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <xsl:template name="situationAutre">
        <fo:table table-layout="fixed" width="100%" border="1px solid black">
            <fo:table-column column-width="proportional-column-width(0.7)"/>
            <fo:table-column column-width="proportional-column-width(0.8)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            <xsl:value-of select="ind-situation/actual-situation"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <xsl:call-template name="blockHandicap"/>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <xsl:template name="blockHandicap">
        <xsl:if test="ind-situation/handicap-travail = 'true' or ind-situation/handicap-adulte = 'true'">
            <fo:table-row text-align="left">
                <fo:table-cell padding="{$padding-cell-tab-info}"
                               number-columns-spanned="3">
                </fo:table-cell>
            </fo:table-row>
            <xsl:if test="not(ind-situation/handicap-travail = 'true' and ind-situation/handicap-adulte = 'true')">
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Travailleur handicapé ou Adulte handicapé
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </xsl:if>
            <xsl:if test="ind-situation/handicap-travail = 'true' and ind-situation/handicap-adulte = 'true'">
                <fo:table-row text-align="left">
                    <fo:table-cell padding="{$padding-cell-tab-info}"
                                   number-columns-spanned="3"
                                   padding-top="5pt">
                        <fo:block font-size="{$para-font-size}">
                            Travailleur handicapé / Adulte handicapé
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template name="bacEqu">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">BACCALAUREAT OU EQUIVALENCE 
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
                  border="1px solid black">
            <xsl:call-template name="baccaleaureat"/>
        </fo:block>
    </xsl:template>

    <xsl:template name="cursusScol">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">CURSUS POST-BACCALAUREAT 
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
                  border="1px solid black">
            <xsl:call-template name="cursusUniversitaire"/>
        </fo:block>
    </xsl:template>

    <xsl:template name="cursusUniversitaire">
        <fo:table table-layout="fixed" width="100%">
            <fo:table-column column-width="proportional-column-width(0.2)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-column column-width="proportional-column-width(0.5)"/>
            <fo:table-column column-width="proportional-column-width(0.5)"/>
            <fo:table-body>
                <xsl:for-each select="ind-cursus-scol-pojos">
                    <fo:table-row text-align="left">
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}" font-weight="bold">
                                <xsl:value-of select="cursus/annee"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:choose>
                                    <xsl:when test="lib-cur">
                                        <xsl:value-of select="lib-cur"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="cursus/lib-dac"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="etablissement/lib-web-etb"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}"
                                       text-align="right">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:choose>
                                    <xsl:when test="cursus/resultat = 'O'">
                                        Obtenu
                                    </xsl:when>
                                    <xsl:when test="cursus/resultat = 'N'">
                                        Non Obtenu
                                    </xsl:when>
                                    <xsl:when test="cursus/resultat != ''">
                                        <xsl:value-of select="cursus/resultat"/>
                                    </xsl:when>
                                </xsl:choose>
                                <xsl:text>   </xsl:text>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}"
                                       text-align="right">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="cursus/lib-mention"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </xsl:for-each>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <xsl:template name="baccaleaureat">
        <fo:table table-layout="fixed" width="100%">
            <fo:table-column column-width="proportional-column-width(1.5)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <xsl:for-each select="ind-bac-pojo">
                    <fo:table-row text-align="left">
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="bac-oux-equ/lib-bac"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="ind-bac/date-obtention"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="mention-niv-bac/lib-mnb"/>

                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                Lieu d'obtention :
                                <xsl:value-of select="pays/lib-pay"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="departement/lib-dep"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="commune/lib-commune"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                        <fo:table-cell padding="{$padding-cell-tab-info}"
                                       number-columns-spanned="3">
                            <fo:block font-size="{$para-font-size}">
                                Etablissement :
                                <xsl:value-of select="etablissement/lib-web-etb"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </xsl:for-each>
            </fo:table-body>
        </fo:table>
    </xsl:template>


    <xsl:template name="cursusPro">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">CURSUS PROFESSIONNEL / STAGES 
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
                  border="1pt solid black">
            <xsl:call-template name="expeProfessionnelle"/>
        </fo:block>
    </xsl:template>

    <xsl:template name="expeProfessionnelle">
        <fo:table table-layout="fixed" width="100%">
            <fo:table-column column-width="proportional-column-width(0.3)"/>
            <fo:table-column column-width="proportional-column-width(0.3)"/>
            <fo:table-column column-width="proportional-column-width(0.6)"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body>
                <xsl:for-each select="ind-cursus-pro">
                    <fo:table-row text-align="left">
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}" font-weight="bold">
                                <xsl:value-of select="annee"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="duree"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="quotite"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="organisme"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row text-align="left">
                        <fo:table-cell padding="{$padding-cell-tab-info}"
                                       number-columns-spanned="4">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="comment"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </xsl:for-each>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <xsl:template name="formationProf">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">FORMATIONS COMPLÉMENTAIRES
                PROFESSIONNALISANTES 
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
                  border="1pt solid black">
            <fo:table table-layout="fixed" width="100%">
                <fo:table-column column-width="proportional-column-width(0.3)"/>
                <fo:table-column column-width="proportional-column-width(0.3)"/>
                <fo:table-column column-width="proportional-column-width(0.6)"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-body>
                    <xsl:for-each select="qualif-pro">
                        <fo:table-row text-align="left">
                            <fo:table-cell padding="{$padding-cell-tab-info}">
                                <fo:block font-size="{$para-font-size}" font-weight="bold">
                                    <xsl:value-of select="annee"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell padding="{$padding-cell-tab-info}">
                                <fo:block font-size="{$para-font-size}">
                                    <xsl:value-of select="duree"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell padding="{$padding-cell-tab-info}">
                                <fo:block font-size="{$para-font-size}">
                                    <xsl:value-of select="libelle"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell padding="{$padding-cell-tab-info}">
                                <fo:block font-size="{$para-font-size}">
                                    <xsl:value-of select="organisme"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </xsl:for-each>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template name="informationCompl">
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="14pt"
                  font-family="{$para-font-family-small}" font-weight="bold">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="14pt"
                  font-family="{$para-font-family-small}" font-weight="bold">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">INFORMATIONS COMPLÉMENTAIRES
                A COMPLÉTER DIRECTEMENT SUR CE DOCUMENT
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="14pt"
                  font-family="{$para-font-family-small}" font-weight="bold">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="none" country="none" font-size="14pt"
                  font-family="{$para-font-family}"
                  text-align="center" font-weight="bold">
            <fo:block>
                <fo:block line-height="110%" hyphenate="false"
                          language="fr" country="FR" font-size="11pt"
                          font-family="{$para-font-family-small}"
                          margin-left="0.212cm" margin-right="-0.594cm"
                          text-indent="0cm" text-align="center">
                    <fo:inline color="#333333" font-weight="bold" font-family="{$para-font-family}">
                        MODALITES ENVISAGÉES DE FINANCEMENT DE VOTRE FORMATION
                    </fo:inline>
                </fo:block>
                <fo:block line-height="110%" hyphenate="false"
                          language="fr" country="FR" font-size="8pt"
                          font-family="{$para-font-family}" margin-left="0.212cm" margin-right="-0.594cm"
                          text-indent="0cm" text-align="center" color="#333333" font-weight="bold">
                    <fo:leader/>
                </fo:block>
                <fo:block line-height="110%" hyphenate="false"
                          language="fr" country="FR" font-size="11pt"
                          font-family="{$para-font-family-small}"
                          margin-left="0cm" margin-right="-0.594cm" text-indent="0cm">
                    <fo:inline font-weight="bold" font-family="{$para-font-family}">
                        Cochez dans le tableau ci-dessous le(s) modalités de financement de votre formation
                    </fo:inline>
                </fo:block>
            </fo:block>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="8pt"
                  font-family="{$para-font-family-small}">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="8pt"
                  font-family="{$para-font-family}" text-align="justify">
            <fo:table table-layout="fixed" width="100%">
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-body>
                    <fo:table-row keep-together.within-column="always">
                        <fo:table-cell padding-left="0.123cm" padding-right="0.123cm" padding-top="0cm"
                                       padding-bottom="0cm" border-left="0.018cm solid #000000"
                                       border-right="none" border-top="0.035cm solid #000000"
                                       border-bottom="0.018cm solid #000000">
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}"
                                      text-align="center"
                                      font-weight="bold">Salariés :
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding-left="0.123cm" padding-right="0.123cm" padding-top="0cm"
                                       padding-bottom="0cm" border-left="0.018cm solid #000000"
                                       border-right="none" border-top="0.035cm solid #000000"
                                       border-bottom="0.018cm solid #000000">
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}"
                                      text-align="center"
                                      font-weight="bold">Demandeurs d'emploi :
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding-left="0.123cm" padding-right="0.123cm" padding-top="0cm"
                                       padding-bottom="0cm" border-left="0.018cm solid #000000"
                                       border-right="0.018cm solid #000000" border-top="0.035cm solid #000000"
                                       border-bottom="0.018cm solid #000000">
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}"
                                      text-align="center"
                                      font-weight="bold">Autre :
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row keep-together.within-column="always">
                        <fo:table-cell padding-left="0.049cm" padding-right="0.049cm" padding-top="0cm"
                                       padding-bottom="0cm" border-left="0.018cm solid #000000"
                                       border-right="none" border-top="0.018cm solid #000000"
                                       border-bottom="0.018cm solid #000000">
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Plan de formation</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Congé Individuel de Formation</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Droit Individuel à  la Formation (DIF)</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Période de professionnalisation</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Financement personnel</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Autre - précisez : ____________</fo:inline>
                                <fo:inline font-size="10pt" font-family="{$para-font-family}">
                                    _______________________________
                                </fo:inline>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding-left="0.049cm" padding-right="0.049cm" padding-top="0cm"
                                       padding-bottom="0cm" border-left="0.018cm solid #000000"
                                       border-right="none" border-top="0.018cm solid #000000"
                                       border-bottom="0.018cm solid #000000">
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Congé Individuel de Formation (CIF-CDD)</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Contrat de professionnalisation</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Aide de la Région</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR"
                                      font-size="11pt" font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Financement personnel</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr"
                                      country="FR"
                                      font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Autre - précisez : ____________</fo:inline>
                                <fo:inline font-size="10pt" font-family="{$para-font-family}">
                                    _______________________________
                                </fo:inline>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding-left="0.049cm" padding-right="0.049cm" padding-top="0cm"
                                       padding-bottom="0cm" border="0.018cm solid #000000">
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">AGEFICE (chefs d'entreprises)</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">FIFPL (professions libérales)</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Financement personnel</fo:inline>
                            </fo:block>
                            <fo:block line-height="110%" hyphenate="false"
                                      language="fr" country="FR" font-size="11pt"
                                      font-family="{$para-font-family-small}">
                                <fo:external-graphic src="url('square.JPG')"/>
                                <fo:inline font-size="10pt">Autre - précisez : ____________</fo:inline>
                                <fo:inline font-size="10pt" font-family="{$para-font-family}">
                                    _______________________________
                                </fo:inline>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="14pt"
                  font-family="{$para-font-family-small}" font-weight="bold">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}"
                  text-align="justify">
            <fo:inline font-weight="bold" font-family="{$para-font-family}">Précisez vos démarches en cours</fo:inline>
            <fo:inline font-family="{$para-font-family}">(noms précis des organismes sollicités, date des demandes et
                réponses obtenues)
            </fo:inline>
            <fo:inline color="#000080" font-family="{$para-font-family}">:
                <fo:leader leader-pattern="dots"/>
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}"
                  text-align="justify">
            ________________________________________________________________________________________
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}"
                  text-align="justify">
            ________________________________________________________________________________________
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}"
                  text-align="justify">
            ________________________________________________________________________________________
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}"
                  text-align="justify">
            ________________________________________________________________________________________
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}"
                  text-align="justify">
            ________________________________________________________________________________________
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="14pt" font-family="{$para-font-family-small}">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family}">
            En cas d'accord obtenu d'un organisme financeur,
            joindre une photocopie des pièces à votre dossier de candidature.
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}">
            <fo:inline font-weight="bold" font-family="{$para-font-family}">
                Veuillez nous tenir régulièrement informés de l'état d'avancement de vos démarches
            </fo:inline>
            <fo:inline font-family="{$para-font-family}">.</fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="4pt" font-family="{$para-font-family}">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family}">
            Pour plus d'information sur les modalités de financement de votre formation :
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt" font-family="{$para-font-family-small}">
            <fo:inline color="#0000ff">
                <fo:inline font-family="{$para-font-family}">http://sfc.univ-rennes1.fr/accueil/financement.html
                </fo:inline>
            </fo:inline>
            <fo:inline font-family="{$para-font-family}">ou contactez le SFC au</fo:inline>
            <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/adresse/phone-number"/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="24pt"
                  font-family="{$para-font-family-small}" text-align="center" font-weight="bold">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family}" margin-left="0.212cm"
                  margin-right="-0.594cm" text-indent="0cm" text-align="center" color="#333333"
                  font-weight="bold">MODALITES D'INFORMATION SUR LA FORMATION
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="4pt" font-family="{$para-font-family-small}"
                  font-weight="bold">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}"
                  font-weight="bold">Par quels moyens avez-vous eu connaissance de cette formation ?
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}" margin-left="0cm" margin-right="0cm"
                  text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            votre employeur
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}" margin-left="0cm"
                  margin-right="0cm" text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            un(e) ancien(ne) étudiant(e) / stagiaire de l'Université
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}" margin-left="0cm"
                  margin-right="0cm" text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            un organisme conseil
            <fo:inline font-size="9pt">(Pôle emploi, OPACIF, cabinet de recrutement, centre de bilan de compétence...)
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}" margin-left="0cm" margin-right="0cm"
                  text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            affiche / plaquette / catalogue
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}" margin-left="0cm"
                  margin-right="0cm"
                  text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            rencontre sur un salon / portes ouvertes
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}" margin-left="0cm" margin-right="0cm"
                  text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            site Internet
            <fo:inline font-size="9pt">(précisez lequel :
                _________________________________________________________________
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0cm" margin-right="0cm" text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            presse écrite
            <fo:inline font-size="9pt">(précisez le journal :
                ______________________________________________________________
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false"
                  language="fr" country="FR" font-size="11pt"
                  font-family="{$para-font-family-small}" margin-left="0cm"
                  margin-right="0cm" text-indent="0.212cm">
            <fo:external-graphic src="url('square.JPG')"/>
            autre moyen
            <fo:inline font-size="9pt">(précisez :
                ______________________________________________________________________
            </fo:inline>
        </fo:block>
    </xsl:template>

    <xsl:template name="entetePage2">
        <fo:block font-family="{$para-font-family-small}" font-size="{$para-font-size-small}"
                  font-weight="bold">
            <fo:table table-layout="fixed" width="100%">
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-body>
                    <fo:table-row text-align="left">
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="individu-pojo/individu/nom-patronymique"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="individu-pojo/individu/prenom"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="individu-pojo/individu/num-dossier-opi"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding="{$padding-cell-tab-info}">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:value-of select="cmi-and-vows-ind/key/commission/code"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template name="pieceJustificative">
        <fo:block padding="5pt" border="1pt solid black">
            <fo:block text-align="center" color="#333333" font-weight="bold"
                      font-family="{$para-font-family}" font-size="11pt" padding-bottom="15pt">
                PIÈCES A JOINDRE AU DOSSIER
            </fo:block>
            <fo:list-block>
                <xsl:for-each select="pieces">
                    <fo:list-item>
                        <fo:list-item-label start-indent="0.5cm">
                            <fo:block>
                                <fo:external-graphic src="url('square.JPG')"/>
                            </fo:block>
                        </fo:list-item-label>
                        <fo:list-item-body start-indent="1cm">
                            <fo:block font-size="{$para-font-size}" padding-top="2pt">
                                <xsl:value-of select="libelle"/>
                            </fo:block>
                        </fo:list-item-body>
                    </fo:list-item>
                </xsl:for-each>
            </fo:list-block>

        </fo:block>
    </xsl:template>


    <xsl:template name="signature">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt">
            <fo:inline font-family="{$para-font-family}">Ce</fo:inline>
            <fo:inline font-weight="bold" font-family="{$para-font-family}">dossier
                de
                candidature est à renvoyer
            </fo:inline>
            <fo:inline font-family="{$para-font-family}">,</fo:inline>
            <fo:inline font-weight="bold" text-decoration="underline" font-family="{$para-font-family}">
                sous quinzaine
            </fo:inline>
            <fo:inline font-family="{$para-font-family}">, avec les pièces
                justificatives
                mentionnées ci-dessus, par courrier postal, à :
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  padding-top="5pt" country="FR" font-size="11pt" font-family="{$para-font-family}"
                  text-align="center">
            <xsl:value-of select="cmi-and-vows-ind/key/contact-commission/corresponding"/>
            -
            <xsl:value-of select="cmi-and-vows-ind/key/commission/libelle"/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family}"
                  text-align="center">
            <fo:block>
                <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/adresse/adr1"/>
                <xsl:if test="cmi-and-vows-ind/key/adresse-pojo/adresse/adr2 != ''">
                    -
                    <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/adresse/adr2"/>
                </xsl:if>
                <xsl:if test="cmi-and-vows-ind/key/adresse-pojo/adresse/adr3 != ''">
                    <fo:block>
                        <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/adresse/adr3"/>
                    </fo:block>
                </xsl:if>
            </fo:block>
            <fo:block>
                <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/adresse/cod-bdi"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/commune/lib-commune"/>
                <xsl:if test="cmi-and-vows-ind/key/adresse-pojo/is-cedex = 'true'">
                    <xsl:text> </xsl:text>
                    CEDEX
                    <xsl:value-of select="cmi-and-vows-ind/key/adresse-pojo/adresse/cedex"/>
                </xsl:if>
            </fo:block>
        </fo:block>
        <fo:block padding-top="10pt" padding-bottom="10pt">
            <fo:inline font-family="{$para-font-family}">- Merci d'agrafer
                ensemble les pages de votre dossier de
                candidature et les pièces jointes.
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family}">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}">
            <fo:inline font-family="{$para-font-family}">Je certifie sur l'honneur
                l'exactitude des renseignements ci-dessous ainsi que des documents
                joints à mon dossier.
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family-small}" font-style="italic">
            En cas de déclaration inexacte ou de dossier incomplet, votre demande
            ou votre inscription sera systématiquement rejetée.
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family}">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}">
            <fo:inline font-family="{$para-font-family}">
                <xsl:value-of select="individu-pojo/individu/nom-patronymique"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="individu-pojo/individu/prenom"/>
            </fo:inline>
            <fo:inline font-family="{$para-font-family}">
                <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                Fait à : ....................le....................
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="10.1cm" margin-right="0cm">
            <fo:inline font-weight="bold" font-family="{$para-font-family}">Signature
            </fo:inline>
            <fo:inline font-family="{$para-font-family}"> :</fo:inline>
        </fo:block>

    </xsl:template>

    <xsl:template name="cadreAdmin">
        <fo:block border="1px solid black" padding="{$padding-cell-tab-info}"
                  background-color="#eaeaea">
            <fo:block font-weight="bold" font-size="8pt" font-family="{$para-font-family}"
                      text-align="center">CADRE RÉSERVÉ A l'ADMINISTRATION
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    - FA : Favorable
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- FR : Favorable
                    sous réserve___________________________________________________
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- EA : En
                    attente
                    de décision
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    - PS : Présélection
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                    Convoqué
                    le :____________________________________________________________
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- LC : En
                    attente
                    sur liste complémentaire
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">Rang :
                    _______
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- DF :
                    Défavorable
                    :
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    - B : Cursus antérieur inadapté
                    <fo:leader leader-pattern="space" leader-length="1.5cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- P : Profil
                    inadapté
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    - C : Niveau insuffisant
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                    <fo:leader leader-pattern="space" leader-length="1.55cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- E : Absent à
                    l'entretien
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    - A : Capacité d'accueil dépassée
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- X : Autre
                    Motif
                    __________________________
                </fo:inline>
            </fo:block>
            <fo:block font-size="8pt" padding-top="3pt" font-family="{$para-font-family}">
                <fo:leader leader-pattern="space" leader-length="1cm"/>
                Proposition d'inscription en :
                _________________________________________________________________________
            </fo:block>
            <fo:block padding-top="3pt">
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-weight="bold"
                           font-family="{$para-font-family}">
                    Date :
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                    Signature du Président de la commission :
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:leader/>
            </fo:block>
        </fo:block>
    </xsl:template>


    <!-- INFO FOR INDIVIDU -->

    <xsl:template name="infoForIndividu">
        <fo:block padding-bottom="25pt">
            <xsl:call-template name="entetePage2"/>
        </fo:block>
        <fo:block line-height="110%" language="fr" country="FR"
                  font-size="{$para-font-size-title}" padding-top="30pt"
                  font-family="{$para-font-family-small}" text-align="center"
                  font-weight="bold">INFORMATIONS A CONSERVER PAR LE CANDIDAT
        </fo:block>
        <fo:block line-height="110%" language="fr" country="FR"
                  font-size="11pt" padding-bottom="30pt" font-family="{$para-font-family-small}"
                  text-align="center">
            <fo:inline color="#333333" font-style="italic" font-family="{$para-font-family}">
                (Ne pas renvoyer avec votre dossier de candidature)
            </fo:inline>
        </fo:block>
        <fo:block padding-top="50pt">
            <xsl:call-template name="contactUniversity"/>
        </fo:block>
        <fo:block padding-top="50pt">
            <xsl:call-template name="suiviTraitement"/>
        </fo:block>
    </xsl:template>


    <xsl:template name="contactUniversity">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">CONTACTS A L'UNIVERSITE 
            </fo:inline>

        </fo:block>

        <fo:block padding="10pt" border="1pt solid black">
            <fo:block font-family="{$para-font-family}">
                <fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
                          padding-top="5pt">
                    Pour toute information, adressez-vous au(x) contact(s)
                    ci-dessous :
                </fo:block>
                <fo:block font-size="{$para-font-size}" padding-top="10pt"
                          text-align="center">
                    <fo:block line-height="110%" hyphenate="false" language="fr"
                              padding-top="5pt" country="FR" font-size="11pt" font-family="{$para-font-family}"
                              text-align="center">
                        <xsl:value-of
                                select="ind-document/cmi-and-vows-ind/key/contact-commission/corresponding"/>
                    </fo:block>
                    <fo:block line-height="110%" hyphenate="false" language="fr"
                              country="FR" font-size="11pt" font-family="{$para-font-family}"
                              text-align="center">
                        <fo:block>
                            <xsl:value-of
                                    select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/adr1"/>
                            <xsl:if test="cmi-and-vows-ind/key/adresse-pojo/adresse/adr2 != ''">
                                -
                                <xsl:value-of
                                        select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/adr2"/>
                            </xsl:if>
                            <xsl:if
                                    test="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/adr3 != ''">
                                <fo:block>
                                    <xsl:value-of
                                            select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/adr3"/>
                                </fo:block>
                            </xsl:if>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of
                                    select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/cod-bdi"/>
                            <xsl:text> </xsl:text>
                            <xsl:value-of
                                    select="ind-document/cmi-and-vows-ind/key/adresse-pojo/commune/lib-commune"/>
                            <xsl:if
                                    test="ind-document/cmi-and-vows-ind/key/adresse-pojo/is-cedex = 'true'">
                                <xsl:text> </xsl:text>
                                CEDEX
                                <xsl:value-of
                                        select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/cedex"/>
                            </xsl:if>
                        </fo:block>
                        <fo:block>
                            Tél :
                            <xsl:value-of
                                    select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/phone-number"/>
                        </fo:block>
                        <fo:block>
                            Fax :
                            <xsl:value-of
                                    select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/fax-number"/>

                        </fo:block>
                        <fo:block>
                            Courriel :
                            <xsl:value-of
                                    select="ind-document/cmi-and-vows-ind/key/adresse-pojo/adresse/mail"/>

                        </fo:block>
                    </fo:block>
                </fo:block>

            </fo:block>
        </fo:block>
    </xsl:template>


    <xsl:template name="suiviTraitement">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">SUIVI DU TRAITEMENT DE VOTRE CANDIDATURE
            </fo:inline>

        </fo:block>

        <fo:block padding="10pt" border="1pt solid black">
            <fo:block font-family="{$para-font-family}">
                <fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
                          padding-top="5pt">
                    Pour suivre le traitement de votre candidature :
                </fo:block>
                <fo:list-block>
                    <fo:list-item>
                        <fo:list-item-label start-indent="1cm">
                            <fo:block>
                                -
                            </fo:block>
                        </fo:list-item-label>
                        <fo:list-item-body start-indent="1.5cm">
                            <fo:block font-size="{$para-font-size}" padding-top="2pt">
                                Consulter fréquemment votre messagerie électronique : des mails
                                vous seront adressés en fonction de l'évolution de votre
                                dossier.
                            </fo:block>
                        </fo:list-item-body>
                    </fo:list-item>
                    <fo:list-item>
                        <fo:list-item-label start-indent="1cm">
                            <fo:block>
                                -
                            </fo:block>
                        </fo:list-item-label>
                        <fo:list-item-body start-indent="1.5cm">
                            <fo:block font-size="{$para-font-size}" padding-top="2pt">
                                Se reconnecter au serveur :
                                <fo:basic-link color="#333"
                                               external-destination="http://candidatures.univ-rennes1.fr">
                                    candidatures.univ-rennes1.fr
                                </fo:basic-link>
                            </fo:block>
                        </fo:list-item-body>
                    </fo:list-item>
                </fo:list-block>
                <fo:block line-height="110%" font-size="11pt" padding-top="15pt"
                          margin-left="0cm" margin-right="-0.594cm" text-indent="1.249cm"
                          text-align="center">
                    <fo:inline font-weight="bold" font-family="{$para-font-family}">Rappel
                        de
                        votre numéro de dossier :
                    </fo:inline>
                    <fo:inline background-color="#ffff00" font-size="14pt"
                               font-weight="bold" font-family="{$para-font-family}">
                        <xsl:value-of
                                select="ind-document/individu-pojo/individu/num-dossier-opi"/>
                    </fo:inline>
                </fo:block>
            </fo:block>
        </fo:block>
    </xsl:template>


</xsl:stylesheet>
