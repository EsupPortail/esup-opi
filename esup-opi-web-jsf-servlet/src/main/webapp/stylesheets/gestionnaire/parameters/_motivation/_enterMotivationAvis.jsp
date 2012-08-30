<%@include file="../../../_include/_include.jsp"%>
<h:form id="formEnterMotivation" >
	<t:div id="blockFormEnterMotivation" styleClass="blockForm">
		<t:div style="width:100%;">
			<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
				styleClass="tableJustWidthMax">
				<e:text value="#{msgs['MOTIV_AVIS.TITLE.ENTER']}" styleClass="section-smallTitle" />
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
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="code"
						value="#{nomenclatureController.nomenclature.code}" size="5"
						maxlength="5"/>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
						for="temoinEnService" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:selectBooleanCheckbox id="temoinEnService"
						value="#{nomenclatureController.nomenclature.temoinEnService}" />
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.LONG_LIB']}" for="libelleLong" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="libelleLong"
						value="#{nomenclatureController.nomenclature.libelle}" size="70"
						maxlength="500"/>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.SHORT_LIB']}" for="libelleShort" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="libelleShort"
						value="#{nomenclatureController.nomenclature.shortLabel}" size="11"
						maxlength="10" />
				</t:panelGroup>
			</e:panelGrid>
		</t:div>
		
	</t:div>
</h:form>

