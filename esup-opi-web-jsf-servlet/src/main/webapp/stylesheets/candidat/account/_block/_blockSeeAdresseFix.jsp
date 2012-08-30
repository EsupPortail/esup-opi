<%@include file="../../../_include/_include.jsp"%>

<t:div id="seeBlockFormAdresseFix" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['ADRESS.FIX']}" styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
					immediate="true"
					action="#{adressController.initUpdate}"
					rendered="#{individuController.pojoIndividu.asRightsToUpdate
					&& individuController.actionEnum.whatAction != individuController.actionEnum.updateAction}">
					<t:updateActionListener
						value="#{adressController.actionEnum.updateAction}"
						property="#{adressController.actionEnum.whatAction}" />								
				</e:commandButton>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:panelGrid styleClass="tableWidthMax" columns="4"
		columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL_PERSO']}" for="seeSeeEmail" />
		</t:panelGroup>
		<e:text id="seeSeeEmail" value="#{individuController.pojoIndividu.individu.adressMail}"
			rendered="#{!individuController.pojoIndividu.isManager}" />
		<h:outputLink value="mailto:#{individuController.pojoIndividu.individu.adressMail}"
			rendered="#{individuController.pojoIndividu.isManager}">
			<h:outputText value="#{individuController.pojoIndividu.individu.adressMail}"/>
		</h:outputLink>
		
		
		<t:panelGroup >
			<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL_ANNUAIRE']}" for="seeEmailAnnuaire" 
					rendered="#{individuController.pojoIndividu.individu.emailAnnuaire != null}"/>
		</t:panelGroup>
		<t:panelGroup >
			<e:text id="seeEmailAnnuaire" value="#{individuController.pojoIndividu.individu.emailAnnuaire}"
					rendered="#{individuController.pojoIndividu.individu.emailAnnuaire != null}" />
		</t:panelGroup>
		
		<e:outputLabel value="#{msgs['ADRESS.TEL_FIX']}" for="seeTelephone" />
		<e:text id="seeTelephone" value="#{adressController.fixAdrPojo.adresse.phoneNumber}"/>
		
		<e:outputLabel value="#{msgs['ADRESS.TEL_POR']}" for="seePortable" />
		<e:text id="seePortable" value="#{individuController.pojoIndividu.individu.numeroTelPortable}"/>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.PAY']}" for="seePaysAdr" />
		</t:panelGroup>
		<e:text id="seePaysAdr" value="#{adressController.fixAdrPojo.pays.libPay}" />
		<t:panelGroup />
		<t:panelGroup />

		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS']}" for="seeAdr1" />
		</t:panelGroup>
		<e:text id="seeAdr1" value="#{adressController.fixAdrPojo.adresse.adr1}" />
		<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="seeAdr2" />
		<e:text id="seeAdr2" value="#{adressController.fixAdrPojo.adresse.adr2}" />
		<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="seeAdr3" />
		<e:text id="seeAdr3" value="#{adressController.fixAdrPojo.adresse.adr3}" />
		
		<t:panelGroup
			rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}">
			<e:outputLabel value="#{msgs['ADRESS.COD_POST']}" for="seeCodePostal" />
		</t:panelGroup>
		<e:text id="seeCodePostal" 
				rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}"
				value="#{adressController.fixAdrPojo.adresse.codBdi}" />
		<e:outputLabel value="#{msgs['ADRESS.VIL']}" 
				rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}"
				for="seeVille" />
		<e:text id="seeVille" 
			rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}"
			value="#{adressController.fixAdrPojo.commune.libCommune}" />
		<e:outputLabel value="#{msgs['ADRESS.VIL.ETR']}" 
				rendered="#{adressController.fixAdrPojo.adresse.codPays 
					!= adressController.codeFrance}"
				for="seeForeignCity" />
		<e:text id="seeForeignCity" 
			rendered="#{adressController.fixAdrPojo.adresse.codPays 
					!= adressController.codeFrance}"
			value="#{adressController.fixAdrPojo.adresse.libComEtr}" />

	</e:panelGrid>
</t:div>
