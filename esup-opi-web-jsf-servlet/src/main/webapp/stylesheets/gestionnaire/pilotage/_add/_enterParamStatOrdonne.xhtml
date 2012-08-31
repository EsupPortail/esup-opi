<%@include file="../../../_include/_include.jsp"%>

<t:panelGrid columns="1" styleClass="displayInfo">
	<e:outputLabel value="#{msgs['FIELD_LABEL.MODIF_ORDONNE']}" styleClass="formHeader"/>
	
	<t:panelGrid columns="2">
		<e:outputLabel value="#{msgs['FIELD_LABEL.TYPE_ORDONNE']}" styleClass="formHeader" for="typeOrdonne"/>
		
		<e:outputLabel value="#{parametreStatController.typeCoordonneOrdonneSelected.libelle}"
			rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction}"/>
			
		<t:panelGroup rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.addAction}">
			<t:selectOneMenu id="typeOrdonne"
				onchange="javascript:{simulateLinkClick('seeParamStatForm:submitTypeOrdonne');}"
				value="#{parametreStatController.selectTypeOrdonne}">
				<f:selectItems value="#{parametreStatController.allTypeOrdonneItems}"/>
			</t:selectOneMenu>
			<e:commandButton id="submitTypeOrdonne" action="#{parametreStatController.clearObjOrdonne}"/>
		</t:panelGroup>
		
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.CHOIX_ORDONNE']}" styleClass="formHeader" for="ordonneModif"/>
		
		<t:panelGrid columns="2">
			<t:panelGrid columns="1">
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE_COORDONNE']} "/>
					<e:inputText value="#{parametreStatController.libelleCoordonne}"/>
				</t:panelGroup>
				<t:panelGrid columns="4" id="ordonneModif">
					<t:selectManyMenu styleClass="manyMenu"
						value="#{parametreStatController.selectObjOrdonneAdd}">
						<f:selectItems value="#{parametreStatController.allObjOrdonneItems}"/>
					</t:selectManyMenu>
					<t:panelGrid columns="1">
						<e:commandButton image="/media/images/fleche_gauche.gif"
							styleClass="icone"
							action="#{parametreStatController.delOrdonneItems}"/>
						<e:commandButton image="/media/images/fleche_droite.gif"
							styleClass="icone"
							action="#{parametreStatController.addOrdonneItems}"/>
					</t:panelGrid>
					<t:panelGroup>
						<t:selectManyMenu styleClass="manyMenu"
							value="#{parametreStatController.selectObjOrdonneDel}">
							<f:selectItems value="#{parametreStatController.objOrdonneItems}"/>
						</t:selectManyMenu>
					</t:panelGroup>
				</t:panelGrid>
			</t:panelGrid>
			
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.addAction}"
					action="#{parametreStatController.addOrdonneCoordonne}"/>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction}"
					action="#{parametreStatController.updateOrdonneCoordonne}"/>
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction}"
					action="#{parametreStatController.clearObjOrdonne}">
					<t:updateActionListener value="#{parametreStatController.actionEnum.emptyAction}" 
						property="#{parametreStatController.actionEnum.whatAction}"/>
				</e:commandButton>
			</t:panelGroup>
		</t:panelGrid>
		
		<t:panelGroup />
		<e:dataTable var="ordonneItem" styleClass="paginatorData" alternateColors="true"
			rendered="#{not empty parametreStatController.listOrdonneCoordonneItems}"
			value="#{parametreStatController.listOrdonneCoordonneItems}">
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.ORDONNE']}"/>
				</f:facet>
				<e:text value="#{ordonneItem.label}"/>
			</t:column>
			
			<t:column>
				<e:commandButton image="/media/images/cancel.png" styleClass="icone"
					action="#{parametreStatController.delOrdonneCoordonne}">
					<t:updateActionListener value="#{ordonneItem.value}"
						property="#{parametreStatController.selectOrdonneCoordonne}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
	</t:panelGrid>
</t:panelGrid>