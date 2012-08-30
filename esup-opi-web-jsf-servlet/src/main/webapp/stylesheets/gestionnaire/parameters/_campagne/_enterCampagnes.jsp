<%@include file="../../../_include/_include.jsp"%>
<h:form id="formEnterMotivation" >
	<t:div id="blockFormEnterMotivation" styleClass="blockForm">
		<t:div style="width:100%;">
			<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
				styleClass="tableJustWidthMax">
				<e:text value="#{msgs['CAMPAGNES.TITLE.ENTER']}" styleClass="section-smallTitle" />
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.addAction}"
						action="#{nomenclatureController.add}" />
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction}"
						action="#{nomenclatureController.update}" />
					<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
						action="#{nomenclatureController.reset}"
						onclick="javascript:{clickAnnuler = true;}"/>
				</t:panelGroup>
			</e:panelGrid>
			<e:panelGrid styleClass="tableWidthMax" columns="4"
				columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
					<t:outputText value="#{msgs['FIELD_LABEL.FORMAT_CAMP']}" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="code"
						value="#{nomenclatureController.nomenclature.code}" size="10"
						maxlength="9"/>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['INDIVIDU.CURSUS.ANNEE_UNI']}" for="codAnu" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="codAnu"
						value="#{nomenclatureController.nomenclature.codAnu}" size="5"
						maxlength="4"/>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.LONG_LIB']}" for="libelleLong" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="libelleLong"
						value="#{nomenclatureController.nomenclature.libelle}" size="50"
						maxlength="500"/>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}"
						for="regimeInscription" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:selectOneMenu id="regimeInscription"
						value="#{nomenclatureController.nomenclature.codeRI}">
						<f:selectItems value="#{nomenclatureController.regimeInscriptionsItems}"/>
					</e:selectOneMenu>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.DATE_DEBUT']}" for="dateDeb" />
					<e:outputLabel value="#{msgs['FIELD_LABEL.FORMAT_DATE']}" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="dateDeb" size="10" maxlength="8"
						value="#{nomenclatureController.nomenclature.dateDebCamp}">
						<f:convertDateTime pattern="ddMMyyyy"
							timeZone="#{sessionController.timezone}" />
						<f:validateLength minimum="8"/>
					</e:inputText>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.DATE_FIN']}" for="dateFin" />
					<e:outputLabel value="#{msgs['FIELD_LABEL.FORMAT_DATE']}" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="dateFin" size="10" maxlength="8"
						value="#{nomenclatureController.nomenclature.dateFinCamp}">
						<f:convertDateTime pattern="ddMMyyyy"
							timeZone="#{sessionController.timezone}" />
						<f:validateLength minimum="8"/>
					</e:inputText>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
						for="temoinEnService" />
				</t:panelGroup>
				<t:panelGroup>
					<e:selectBooleanCheckbox id="temoinEnService"
						value="#{nomenclatureController.nomenclature.temoinEnService}" />
				</t:panelGroup>
			</e:panelGrid>
		</t:div>
		
	</t:div>
</h:form>

