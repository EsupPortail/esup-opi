<%@include file="../../../_include/_include.jsp"%>

<t:panelGrid columns="1" styleClass="displayInfo">
	<e:outputLabel value="#{msgs['FIELD_LABEL.MODIF_ABSCISSE']}" styleClass="formHeader"/>
	
	<t:panelGrid columns="2">
		<e:outputLabel value="#{msgs['FIELD_LABEL.TYPE_ABSCISSE']}" styleClass="formHeader" for="typeAbscisse"/>
		
		<e:outputLabel value="#{parametreStatController.typeCoordonneAbscisseSelected.libelle}"
			rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction}"/>
		
		<t:panelGroup rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.addAction}">
			<t:selectOneMenu id="typeAbscisse"
				onchange="javascript:{simulateLinkClick('seeParamStatForm:submitTypeAbscisse');}"
				value="#{parametreStatController.selectTypeAbscisse}">
				<f:selectItems value="#{parametreStatController.allTypeAbscisseItems}"/>
			</t:selectOneMenu>
			<e:commandButton id="submitTypeAbscisse" action="#{parametreStatController.clearObjAbscisse}"/>
		</t:panelGroup>
		
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.CHOIX_ABSCISSE']}" styleClass="formHeader" for="abscisseModif"/>
		
		<t:panelGrid columns="2">
			<t:panelGrid columns="1">
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE_COORDONNE']} "/>
					<e:inputText value="#{parametreStatController.libelleCoordonne}" />
				</t:panelGroup>
				<t:panelGrid columns="4" id="abscisseModif">
					<t:selectManyMenu styleClass="manyMenu"
						value="#{parametreStatController.selectObjAbscisseAdd}">
						<f:selectItems value="#{parametreStatController.allObjAbscisseItems}"/>
					</t:selectManyMenu>
					<t:panelGrid columns="1">
						<e:commandButton image="/media/images/fleche_gauche.gif"
							styleClass="icone"
							action="#{parametreStatController.delAbscisseItems}"/>
						<e:commandButton image="/media/images/fleche_droite.gif"
							styleClass="icone"
							action="#{parametreStatController.addAbscisseItems}"/>
					</t:panelGrid>
					<t:panelGroup>
						<t:selectManyMenu styleClass="manyMenu"
							value="#{parametreStatController.selectObjAbscisseDel}">
							<f:selectItems value="#{parametreStatController.objAbscisseItems}"/>
						</t:selectManyMenu>
					</t:panelGroup>
				</t:panelGrid>
			</t:panelGrid>
			
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.addAction}"
					action="#{parametreStatController.addAbscisseCoordonne}"/>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction}"
					action="#{parametreStatController.updateAbscisseCoordonne}"/>
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction}"
					action="#{parametreStatController.clearObjAbscisse}">
					<t:updateActionListener value="#{parametreStatController.actionEnum.emptyAction}" 
						property="#{parametreStatController.actionEnum.whatAction}"/>
				</e:commandButton>
			</t:panelGroup>
		</t:panelGrid>
		
		<t:panelGroup />
		<e:dataTable var="abscisseItem" styleClass="paginatorData" alternateColors="true"
			rendered="#{not empty parametreStatController.listAbscisseCoordonneItems}"
			value="#{parametreStatController.listAbscisseCoordonneItems}">
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.ABSCISSE']}"/>
				</f:facet>
				<e:text value="#{abscisseItem.label}"/>
			</t:column>
			
			<t:column>
				<e:commandButton image="/media/images/cancel.png" styleClass="icone"
					action="#{parametreStatController.delAbscisseCoordonne}">
					<t:updateActionListener value="#{abscisseItem.value}"
						property="#{parametreStatController.selectAbscisseCoordonne}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
	</t:panelGrid>
</t:panelGrid>