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
                                Dossier de candidature � renvoyer : page
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
        <fo:block font-family="{$para-font-family}" padding-top="20px"
                  padding-bottom="20px">
            <fo:block>
                <fo:inline font-size="{$para-font-family}" font-family="{$para-font-family}">
                    Date limite d'envoi du dossier :
                </fo:inline>
                <fo:inline font-size="{$para-font-family}" font-weight="bold"
                           font-family="{$para-font-family}">
                    <xsl:value-of
                            select="cmi-and-vows-ind/key/commission/calendar-cmi/dat-end-back-dossier"/>
                </fo:inline>
            </fo:block>
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
                                          text-align="center" color="#808080" font-weight="bold">d'identit�
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="11pt" font-family="{$para-font-family}"
                                          text-align="center" color="#808080" font-weight="bold">�
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
                                            T�l :
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
                                          language="fr" country="FR" font-size="4pt" font-family="{$para-font-family}"
                                          text-align="center" font-weight="bold">
                                    <fo:leader/>
                                </fo:block>
                                <fo:block line-height="110%" hyphenate="false"
                                          language="fr" country="FR" font-size="{$para-font-size}"
                                          font-family="{$para-font-family-small}" text-align="center">
                                    <fo:inline font-size="{$para-font-size}" font-weight="bold"
                                               font-family="{$para-font-family}">Dossier n��:
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
                                               font-family="{$para-font-family}">Commission�:
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
        <fo:block padding-bottom="15px" font-family="{$para-font-family}"
                  font-size="{$para-font-size}">
            <xsl:call-template name="bacEqu"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="cursusScol"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="cursusPro"/>
        </fo:block>
        <fo:block padding-bottom="15pt">
            <xsl:call-template name="formationProf"/>
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
                       font-family="{$para-font-family}">CANDIDATURES�
            </fo:inline>
            <fo:inline color="#333333" font-size="10pt" font-style="italic"
                       font-weight="bold" font-family="{$para-font-family}">(ordre
                de pr�f�rence � pr�ciser)
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
                                    choix n� : ___
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
                       font-family="{$para-font-family}">COORDONNEES PERSONNELLES�
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
                            Civilit� :
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
                            Pr�nom :
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
                            N�(e) le :
                            <xsl:value-of select="individu-pojo/individu/date-naissance"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}">
                        <fo:block font-size="{$para-font-size}">
                            � :
                            <xsl:value-of select="individu-pojo/individu/ville-naissance"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding="{$padding-cell-tab-info}">
                        <fo:block font-size="{$para-font-size}">
                            Nationalit� :
                            <xsl:value-of select="individu-pojo/nationalite/lib-nat"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row text-align="left">
                    <fo:table-cell padding="0pt" number-columns-spanned="2">
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
                                T�l. portable :
                                <xsl:value-of select="individu-pojo/individu/numero-tel-portable"/>
                            </fo:block>
                            <fo:block>
                                T�l. fixe :
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


    <xsl:template name="bacEqu">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">BACCALAUREAT OU EQUIVALENCE�
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
                       font-family="{$para-font-family}">CURSUS POST-BACCALAUREAT�
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
                       font-family="{$para-font-family}">CURSUS PROFESSIONNEL / STAGES�
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
                       font-family="{$para-font-family}">FORMATIONS COMPLEMENTAIRES
                PROFESSIONNALISANTES�
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
                PI�CES A JOINDRE AU DOSSIER
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
                candidature est � renvoyer
            </fo:inline>
            <fo:inline font-family="{$para-font-family}">, avec les pi�ces
                justificatives
                mentionn�es ci-dessus, par courrier postal, �:
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
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family}"
                  text-align="center">
            <fo:leader/>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-family="{$para-font-family-small}" text-align="center">
            <fo:inline font-size="{$para-font-size-title}" font-weight="bold"
                       font-family="{$para-font-family}">Date limite d'envoi du dossier :
            </fo:inline>
            <fo:inline background-color="#ffff00" font-size="{$para-font-size-title}"
                       font-weight="bold" font-family="{$para-font-family}">
                <xsl:value-of select="cmi-and-vows-ind/key/commission/calendar-cmi/dat-end-back-dossier"/>
            </fo:inline>
        </fo:block>
        <xsl:if test="cmi-and-vows-ind/key/commission/calendar-cmi/comm-dat-end-back">
            <fo:block font-family="{$para-font-family}" text-align="center"
                      font-style="italic" font-weight="bold">
                <xsl:value-of select="cmi-and-vows-ind/key/commission/calendar-cmi/comm-dat-end-back"/>
            </fo:block>
        </xsl:if>
        <fo:block font-family="{$para-font-family-small}" text-align="center"
                  font-style="italic">
            (la date d'affranchissement faisant foi)
        </fo:block>
        <fo:block padding-top="10pt" padding-bottom="10pt">
            <fo:inline font-family="{$para-font-family}">- Merci de NE PAS AGRAFER
                votre dossier de candidature ni les pi�ces
                jointes qui l'accompagne.
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
                joints � mon dossier.
            </fo:inline>
        </fo:block>
        <fo:block font-family="{$para-font-family-small}" font-style="italic">
            En cas de d�claration inexacte ou de dossier incomplet, votre demande
            ou votre inscription sera syst�matiquement rejet�e.
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
                Fait �: ....................le....................
            </fo:inline>
        </fo:block>
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="10.1cm" margin-right="0cm">
            <fo:inline font-weight="bold" font-family="{$para-font-family}">Signature
            </fo:inline>
            <fo:inline font-family="{$para-font-family}">�:</fo:inline>
        </fo:block>

    </xsl:template>

    <xsl:template name="cadreAdmin">
        <fo:block border="1px solid black" padding="{$padding-cell-tab-info}"
                  background-color="#eaeaea">
            <fo:block font-weight="bold" font-size="8pt" font-family="{$para-font-family}"
                      text-align="center">CADRE R�SERV� A l'ADMINISTRATION
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
                    sous r�serve___________________________________________________
                </fo:inline>
            </fo:block>
            <fo:block>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    <fo:leader leader-pattern="space" leader-length="1cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- EA�: En
                    attente
                    de d�cision
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
                    - PS�: Pr�s�lection
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                    <fo:leader leader-pattern="space" leader-length="1.249cm"/>
                    Convoqu�
                    le�:____________________________________________________________
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
                    sur liste compl�mentaire
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">Rang�:
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
                    D�favorable
                    :
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">
                    - B : Cursus ant�rieur inadapt�
                    <fo:leader leader-pattern="space" leader-length="1.5cm"/>
                </fo:inline>
                <fo:inline>
                    <fo:external-graphic src="url('square.JPG')"/>
                </fo:inline>
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- P : Profil
                    inadapt�
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
                <fo:inline font-size="8pt" font-family="{$para-font-family}">- E : Absent �
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
                    - A�: Capacit� d'accueil d�pass�e
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
                Proposition d'inscription en�:
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
                    Signature du Pr�sident de la commission :
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
        <fo:block>
            <xsl:call-template name="calendrier"/>
        </fo:block>
        <fo:block padding-top="50pt">
            <xsl:call-template name="contactUniversity"/>
        </fo:block>
        <fo:block padding-top="50pt">
            <xsl:call-template name="suiviTraitement"/>
        </fo:block>
    </xsl:template>

    <xsl:template name="calendrier">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">CALENDRIER DE RECRUTEMENT�
            </fo:inline>

        </fo:block>

        <fo:block padding="10pt" border="1pt solid black">
            <fo:block font-family="{$para-font-family}">
                <xsl:for-each select="ind-document/cmi-and-vows-ind/value">
                    <fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
                              font-weight="bold" padding-left="0.5cm" padding-bottom="5pt"
                              padding-top="10pt">
                        -
                        <fo:inline font-size="{$para-font-size}">
                            <xsl:value-of select="lib-web-vet"/>
                            :
                        </fo:inline>
                    </fo:block>
                    <fo:block font-size="{$para-font-size}">
                        Date limite d'envoi du dossier de candidature�:
                        <xsl:value-of select="../key/commission/calendar-cmi/dat-end-back-dossier"/>
                    </fo:block>
                    <xsl:variable name="cod-etp-dto" select="cod-etp"/>
                    <xsl:variable name="cod-vrs-vet-dto" select="cod-vrs-vet"/>
                    <xsl:for-each select="../key/commission/traitement-cmi">
                        <xsl:if test="version-etp-opi/cod-etp = $cod-etp-dto
										and version-etp-opi/cod-vrs-vet = $cod-vrs-vet-dto">
                            <fo:block font-size="{$para-font-size}">
                                <xsl:if test="selection/result-selection != ''">
                                    <fo:block>
                                        Affichage des r�sultats de pr�s�lection :
                                        <xsl:value-of select="selection/result-selection"/>
                                    </fo:block>
                                </xsl:if>
                                <xsl:if test="selection/periode-admissibilite != ''">
                                    <fo:block>
                                        Entretiens ou �preuves de s�lection :
                                        <xsl:value-of select="selection/periode-admissibilite"/>
                                    </fo:block>
                                </xsl:if>
                            </fo:block>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:for-each>
            </fo:block>
        </fo:block>
    </xsl:template>


    <xsl:template name="contactUniversity">
        <fo:block line-height="110%" hyphenate="false" language="fr"
                  country="FR" font-size="11pt" font-family="{$para-font-family-small}"
                  margin-left="0.212cm" margin-right="-0.594cm" text-indent="0cm"
                  text-align="center">
            <fo:inline color="#333333" font-weight="bold"
                       font-family="{$para-font-family}">CONTACTS A L'UNIVERSITE�
            </fo:inline>

        </fo:block>

        <fo:block padding="10pt" border="1pt solid black">
            <fo:block font-family="{$para-font-family}">
                <fo:block font-size="{$para-font-size}" font-family="{$para-font-family}"
                          padding-top="5pt">
                    Pour toute information, adressez-vous au(x) contact(s)
                    ci-dessous�:
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
                            T�l :
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
                    Pour suivre le traitement de votre candidature�:
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
                                Consulter fr�quemment votre messagerie �lectronique�: des mails
                                vous seront adress�s en fonction de l'�volution de votre
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
                                Se reconnecter au serveur�:
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
                        votre num�ro de dossier�:
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
