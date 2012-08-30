<%@include file="../../../_include/_include.jsp"%>
<e:panelGrid styleClass="tableWidthMax" columns="4"
	columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<t:panelGroup>
		<e:inputText id="code"
			value="#{nomenclatureController.nomenclature.code}" size="5"
			maxlength="5"
			rendered="#{nomenclatureController.actionEnum.whatAction 
							!= nomenclatureController.actionEnum.readAction}" />
		<e:text value="#{nomenclatureController.nomenclature.code}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
							== nomenclatureController.actionEnum.readAction}" />
	</t:panelGroup>
	<t:panelGroup>
		<e:outputLabel value="#{msgs['PJ.IS_FOR_ALL_VET']}" for="isForAllVet" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<t:panelGroup>
		<e:selectBooleanCheckbox id="isForAllVet" onclick="submit();"
			disabled="#{!nomenclatureController.rightPjForAllVet}"
			value="#{nomenclatureController.nomenclature.isForAllVet}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								!= nomenclatureController.actionEnum.readAction}" />
		<e:selectBooleanCheckbox id="isForAllVet2"
			disabled="#{!nomenclatureController.rightPjForAllVet}"
			value="#{nomenclatureController.nomenclature.isForAllVet}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								== nomenclatureController.actionEnum.readAction}"
			readonly="true" />
	</t:panelGroup>
	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}" for="libelle" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<t:panelGroup>
		<e:inputText id="libelle"
			value="#{nomenclatureController.nomenclature.libelle}" size="70"
			maxlength="500"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								!= nomenclatureController.actionEnum.readAction}" />
		<e:text value="#{nomenclatureController.nomenclature.libelle}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								== nomenclatureController.actionEnum.readAction}" />
	</t:panelGroup>
	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
			for="temoinEnService" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<t:panelGroup>
		<e:selectBooleanCheckbox id="temoinEnService"
			value="#{nomenclatureController.nomenclature.temoinEnService}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								!= nomenclatureController.actionEnum.readAction}" />
		<e:selectBooleanCheckbox id="temoinEnService2"
			value="#{nomenclatureController.nomenclature.temoinEnService}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								== nomenclatureController.actionEnum.readAction}"
			readonly="true" />
	</t:panelGroup>

	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}"
			for="regimeInscription" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<t:panelGroup>
		<e:selectOneMenu id="regimeInscription"
			value="#{nomenclatureController.nomenclature.codeRI}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								!= nomenclatureController.actionEnum.readAction}">
			<f:selectItems value="#{nomenclatureController.regimeInscriptionsItems}"/>
		</e:selectOneMenu>
		<e:selectOneMenu id="regimeInscription2" readonly="true"
			value="#{nomenclatureController.nomenclature.codeRI}"
			rendered="#{nomenclatureController.actionEnum.whatAction 
								== nomenclatureController.actionEnum.readAction}">
			<f:selectItems value="#{nomenclatureController.regimeInscriptionsItems}"/>
		</e:selectOneMenu>
	</t:panelGroup>
	<t:panelGroup />
	<t:panelGroup />
</e:panelGrid>