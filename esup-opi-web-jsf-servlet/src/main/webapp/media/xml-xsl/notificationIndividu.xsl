<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:variable name="para-font-size">
		11pt
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
			xmlns:fox="http://xml.apache.org/fop/extensions">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="all"
					page-height="29.699cm" page-width="20.999cm" margin-right="2cm"
					margin-left="2cm" margin-bottom="1cm" margin-top="0.5cm">
					<fo:region-body margin-top="0cm" margin-bottom="1cm" />
					<fo:region-before extent="1.5cm" />
					<fo:region-after extent="0.5cm" region-name="xsl-region-after" />
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
		<xsl:for-each select="notification-opinion">
			<xsl:if test="voeux-favorable != ''">
				<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
					<xsl:call-template name="voeuxFavorables" />
				</fo:block>
				<xsl:if test="voeux-defavorable != ''">
					<fo:block break-after='page' />
				</xsl:if>
			</xsl:if>
			<xsl:if test="voeux-defavorable != ''">
				<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
					<xsl:call-template name="voeuxDefavorables" />
				</fo:block>
			</xsl:if>
			<xsl:if test="voeux-favorable-appel != ''">
				<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
					<xsl:call-template name="voeuxFavorablesAppel" />
				</fo:block>
				<xsl:if test="voeux-defavorable != ''">
					<fo:block break-after='page' />
				</xsl:if>
			</xsl:if>
			<xsl:if test="voeux-defavorable-appel != ''">
				<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
					<xsl:call-template name="voeuxDefavorablesAppel" />
				</fo:block>
			</xsl:if>
			<xsl:if test="position() != last()">
				<fo:block break-after='page' />
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="voeuxFavorables">
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="10cm" padding-top="2.5cm">
			<xsl:call-template name="dateDuJour" />
			<xsl:call-template name="adresse" />
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="40pt" text-align="justify">
			<xsl:if test="sexe = 'M'">
				Monsieur,
			</xsl:if>
			<xsl:if test="sexe = 'F'">
				Madame,
			</xsl:if>
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			Apr�s examen de
			votre dossier par la Commission p�dagogique, j'ai le plaisir de vous
			informer que vous �tes autoris� � vous inscrire � la (ou aux)
			formation(s) suivante(s) : 
		</fo:block>
		<xsl:for-each select="voeux-favorable">
			<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
				<xsl:call-template name="lesVoeux" />
			</fo:block>
		</xsl:for-each>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			La v�rification
			de vos dipl�mes sera effectu�e, lors de votre inscription
			administrative, avant d�livrance de votre carte �tudiant.
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">

			Vous devez imp�rativement vous reconnecter
				<xsl:if test="date-cloture">
					avant le
					<xsl:text> </xsl:text>
					<xsl:value-of select="date-cloture" />
				</xsl:if>

				au serveur Candidatures Rennes1 (www.candidatures.univ-rennes1.fr) pour
				indiquer si vous acceptez d'int�grer cette (ou l'une de ces)
				formation(s) ou si vous y renoncez.
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			L'absence de
			confirmation de votre part pendant la p�riode indiqu�e sera
			consid�r�e comme un d�sistement. 
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			Si vous confirmez
			votre entr�e � l'Universit� de Rennes 1, vous serez inform� par mail
			des modalit�s de votre inscription administrative.
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			Je vous rappelle que cette proposition d'inscription n'est valable
			que pour l'ann�e universitaire
			<xsl:value-of select="periode-scolaire" />
			. Si vous souhaitez diff�rer votre entr�e en formation, il conviendra
			de renouveler votre candidature.
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			Pour toute
			information compl�mentaire, merci de contacter :
		</fo:block>

		<xsl:call-template name="contactCGE" />

		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="5cm" padding-top="15pt" text-align="justify">
			Cordialement,
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="5cm" padding-top="10pt" text-align="justify">
			P/ Le Pr�sident de
			l'Universit� et par d�l�gation,
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="5cm" padding-top="10pt" text-align="justify">
			Le Responsable du
			Service de la Scolarit� 
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="5cm" padding-top="10pt" text-align="center">
			<xsl:if test="signataire">
				<xsl:value-of select="signataire/nom-sig" />
			</xsl:if>
		</fo:block>
		
		<xsl:call-template name="RappelNumDossier" />
		<fo:block break-after='page' />
	</xsl:template>

	<xsl:template name="voeuxDefavorables">
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="10cm" padding-top="2.5cm">
			<xsl:call-template name="dateDuJour" />
			<xsl:call-template name="adresse" />
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="40pt" text-align="justify">
			<xsl:if test="sexe = 'M'">
				Monsieur,
			</xsl:if>
			<xsl:if test="sexe = 'F'">
				Madame,
			</xsl:if>
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			J'ai le regret de
			vous informer, qu'apr�s examen de votre dossier, la Commission
			p�dagogique n'a pu retenir votre candidature pour les motifs
			suivants�:
		</fo:block>
		<xsl:for-each select="voeux-defavorable">
			<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
				<xsl:call-template name="lesVoeux" />
			</fo:block>
		</xsl:for-each>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			Si vous souhaitez
			faire appel de cette d�cision, vous pouvez former un recours aupr�s
			du Pr�sident de l'universit� ou du tribunal administratif de Rennes
			(voies et d�lais de recours pr�cis�s au verso).
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			Pour toute
			information compl�mentaire, je vous remercie de contacter�:
		</fo:block>

		<xsl:call-template name="contactCGE" />

		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			Je vous prie
			d'agr�er, Madame, Monsieur, l'expression de ma consid�ration
			distingu�e. 
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="3cm" padding-top="35pt" text-align="justify">
			P/ Le Pr�sident de
			l'Universit� et par d�l�gation,
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}" margin-left="3cm"
			padding-top="5pt" text-align="justify">
			<xsl:choose>
				<xsl:when test="signataire">
					<xsl:value-of select="signataire/qua-sig" />
				</xsl:when>
				<xsl:otherwise>
					Le Responsable du Service de la Scolarit�
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="3cm" padding-top="80pt" text-align="center">
			<xsl:if test="signataire">
				<xsl:value-of select="signataire/nom-sig" />
			</xsl:if>
		</fo:block>
		<xsl:call-template name="RappelNumDossier" />
		<fo:block break-after='page' />

		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size-title}"
			margin-left="2cm" padding-top="6cm" text-align="justify" font-weight="bold">
			VISAS
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="3.5cm" padding-top="25pt" text-align="justify"
			text-indent="-1.5cm">
			D�cret n�85-906 du 23 ao�t 1985 fixant les conditions de
			validation des �tudes, exp�riences professionnelles ou acquis
			personnels en vue de l'acc�s aux diff�rents niveaux de l'enseignement
			sup�rieur,
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="3.5cm" padding-top="15pt" text-align="justify"
			text-indent="-1.5cm">
			D�cret n�2002-481 du 8 avril 2002 relatif aux grades et
			titres universitaires et aux dipl�mes nationaux.
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size-title}"
			margin-left="2cm" padding-top="20pt" text-align="justify"
			font-weight="bold">
			DELAIS ET VOIES DE RECOURS
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="2cm" padding-top="15pt" text-align="justify"
			text-indent="1.5cm">
			Si vous souhaitez faire appel de cette d�cision, vous
			disposez d'un d�lai de deux mois pour�:
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="2cm" padding-top="5pt" text-align="justify" text-indent="1.5cm">
			- soit former un recours gracieux aupr�s du Pr�sident de l'universit�
			de Rennes 1�;
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="2cm" padding-top="5pt" text-align="justify" text-indent="1.5cm">
			- soit former un recours contentieux aupr�s du tribunal administratif
			de Rennes.
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="2cm" padding-top="15pt" text-align="justify"
			text-indent="1.5cm">
			Dans le cas o� vous choisissez de former un recours
			gracieux, vous disposez d'un nouveau d�lai de deux mois,
			soit �
			compter de la notification de la nouvelle d�cision,
			soit � compter de
			l'expiration du d�lai implicite de d�cision de deux mois, pour
			contester celle-ci devant le tribunal administratif.
		</fo:block>

	</xsl:template>
	<xsl:template name="voeuxFavorablesAppel">
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="10cm" padding-top="2.5cm">
			<xsl:call-template name="dateDuJour" />
			<xsl:call-template name="adresse" />
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="40pt" text-align="justify">
			<xsl:if test="sexe = 'M'">
				Monsieur,
			</xsl:if>
			<xsl:if test="sexe = 'F'">
				Madame,
			</xsl:if>
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			A la suite du recours que vous avez formul�, 
			votre dossier de candidature a �t� r�examin� 
			par la Commission p�dagogique. 
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			Au vu des nouveaux �l�ments fournis, 
			la Commission p�dagogique a un �mis un avis 
			favorable pour votre inscription en :
		</fo:block>
		<xsl:for-each select="voeux-favorable-appel">
			<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
				<xsl:call-template name="lesVoeux" />
			</fo:block>
		</xsl:for-each>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			Pour toute
			information compl�mentaire, merci de contacter :
		</fo:block>

		<xsl:call-template name="contactCGE" />

		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			text-indent="1.25cm" padding-top="15pt" text-align="justify">
			Cordialement,
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="5cm" padding-top="10pt" text-align="justify">
			P/ Le Pr�sident de
			l'Universit� et par d�l�gation,
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="5cm" padding-top="10pt" text-align="justify">
			Le Responsable du
			Service de la Scolarit� 
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="5cm" padding-top="10pt" text-align="center">
			<xsl:if test="signataire">
				<xsl:value-of select="signataire/nom-sig" />
			</xsl:if>
		</fo:block>
		<xsl:call-template name="RappelNumDossier" />
		<fo:block break-after='page' />
	</xsl:template>

	<xsl:template name="voeuxDefavorablesAppel">
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="10cm" padding-top="2.5cm">
			<xsl:call-template name="dateDuJour" />
			<xsl:call-template name="adresse" />
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="40pt" text-align="justify">
			<xsl:if test="sexe = 'M'">
				Monsieur,
			</xsl:if>
			<xsl:if test="sexe = 'F'">
				Madame,
			</xsl:if>
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			A la suite du recours que vous avez formul�, 
			votre dossier de candidature a �t� r�examin� 
			par la Commission p�dagogique.
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			Cependant, j'ai le regret de vous 
			informer que la Commission p�dagogique 
			a maintenu l'avis pr�c�demment �mis :
		</fo:block>
		<xsl:for-each select="voeux-defavorable-appel">
			<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}">
				<xsl:call-template name="lesVoeux" />
			</fo:block>
		</xsl:for-each>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="10pt" text-align="justify" text-indent="1.25cm">
			Pour toute
			information compl�mentaire, je vous remercie de contacter�:
		</fo:block>

		<xsl:call-template name="contactCGE" />

		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm">
			Je vous prie
			d'agr�er, Madame, Monsieur, l'expression de ma consid�ration
			distingu�e. 
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="3cm" padding-top="35pt" text-align="justify">
			P/ Le Pr�sident de
			l'Universit� et par d�l�gation,
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}" margin-left="3cm"
			padding-top="5pt" text-align="justify">
			<xsl:choose>
				<xsl:when test="signataire">
					<xsl:value-of select="signataire/qua-sig" />
				</xsl:when>
				<xsl:otherwise>
					Le Responsable du Service de la Scolarit�
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			margin-left="3cm" padding-top="80pt" text-align="center">
			<xsl:if test="signataire">
				<xsl:value-of select="signataire/nom-sig" />
			</xsl:if>
		</fo:block>
		<xsl:call-template name="RappelNumDossier" />
	</xsl:template>

	<xsl:template name="RappelNumDossier">
		<fo:block font-family="{$para-font-family}" font-size="{$para-font-size}"
			padding-top="15pt" text-align="justify" text-indent="1.25cm"
			font-style="italic">
			<xsl:if test="num-dossier-opi">
				Rappel : votre num�ro de dossier de candidature est le :
				<xsl:value-of select="num-dossier-opi" />
			</xsl:if>
		</fo:block>
	</xsl:template>

	<xsl:template name="dateDuJour">
		Le
		<xsl:value-of select="date-du-jour" />
	</xsl:template>

	<xsl:template name="contactCGE">
		<fo:block font-size="{$para-font-size}" padding-top="10pt"
			margin-left="3cm">
			<fo:block font-size="{$para-font-size}" padding-top="2pt">
				<xsl:value-of select="nom-commission" />
			</fo:block>
			<fo:block font-size="{$para-font-size}">
				<xsl:value-of select="coordonnees-contact/adresse/adr1" />
			</fo:block>
			<fo:block font-size="{$para-font-size}">
				<xsl:value-of select="coordonnees-contact/adresse/adr2" />
			</fo:block>
			<fo:block font-size="{$para-font-size}">
				<xsl:value-of select="coordonnees-contact/adresse/adr3" />
			</fo:block>
			<fo:block font-size="{$para-font-size}">
				<xsl:value-of select="coordonnees-contact/commune/cod-com" />
				<xsl:text> </xsl:text>
				<xsl:value-of select="coordonnees-contact/commune/lib-commune" />
			</fo:block>
			<xsl:if test="coordonnees-contact/adresse/phone-number != ''">
				<fo:block font-size="{$para-font-size}">
					T�l�phone :
					<xsl:value-of select="coordonnees-contact/adresse/phone-number" />
				</fo:block>
			</xsl:if>
			<xsl:if test="coordonnees-contact/adresse/fax-number != ''">
				<fo:block font-size="{$para-font-size}">
					Fax :
					<xsl:value-of select="coordonnees-contact/adresse/fax-number" />
				</fo:block>
			</xsl:if>
			<xsl:if test="coordonnees-contact/adresse/mail != ''">
				<fo:block font-size="{$para-font-size}">
					Mail :
					<xsl:value-of select="coordonnees-contact/adresse/mail" />
				</fo:block>
			</xsl:if>
			<xsl:if test="coordonnees-contact/adresse/is-cedex = true">
				<xsl:text> </xsl:text>
				CEDEX
				<xsl:value-of select="coordonnees-contact/adresse/cedex" />
			</xsl:if>
		</fo:block>
	</xsl:template>

	<xsl:template name="lesVoeux">
		<fo:block font-size="{$para-font-size}" padding-top="10pt"
			margin-left="2cm">
			-
			<xsl:value-of select="vrs-etape/lib-web-vet" />
			<xsl:text> : </xsl:text>
			<xsl:value-of select="avis-en-service/motivation-avis/libelle" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="avis-en-service/commentaire" />
		</fo:block>
	</xsl:template>

	<xsl:template name="adresse">
		<fo:block font-size="11pt" padding-top="15pt" font-weight="bold">
			<xsl:if test="sexe = 'M'">
				Monsieur
			</xsl:if>
			<xsl:if test="sexe = 'F'">
				Madame
			</xsl:if>
			<xsl:value-of select="nom" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="prenom" />
		</fo:block>
		<fo:block font-size="11pt" font-weight="bold">
			<xsl:value-of select="adresse-etu/adresse/adr1" />
		</fo:block>
		<fo:block font-size="11pt" font-weight="bold">
			<xsl:value-of select="adresse-etu/adresse/adr2" />
		</fo:block>
		<fo:block font-size="11pt" font-weight="bold">
			<xsl:value-of select="adresse-etu/adresse/adr3" />
		</fo:block>
		<fo:block font-size="11pt" font-weight="bold">
			<xsl:choose>
				<xsl:when test="adresse-etu/commune">
					<xsl:value-of select="adresse-etu/adresse/cod-bdi" />
					<xsl:text> </xsl:text>
					<xsl:value-of select="adresse-etu/commune/lib-commune" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="adresse-etu/adresse/lib-com-etr" />
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
		<fo:block font-size="11pt" font-weight="bold">
			<xsl:value-of select="adresse-etu/pays/lib-pay" />
		</fo:block>
		<xsl:if test="adresse-etu/adresse/adr2 = ''">
			<fo:block font-size="11pt">
				<xsl:text> </xsl:text>
			</fo:block>
		</xsl:if>
		<xsl:if test="adresse-etu/adresse/adr3 = ''">
			<fo:block font-size="11pt">
				<xsl:text> </xsl:text>
			</fo:block>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
