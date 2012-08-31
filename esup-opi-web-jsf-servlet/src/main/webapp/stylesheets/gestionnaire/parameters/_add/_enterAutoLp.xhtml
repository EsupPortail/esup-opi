<%@include file="../../../_include/_include.jsp"%>
<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi" styleClass="tableJustWidthMax">
	<e:text value="#{msgs['AUTO_LP.TITLE.ENTER']}" styleClass="section-smallTitle"
		rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.addAction}"/>
	<e:text value="#{msgs['AUTO_LP.TITLE.UPDATE']}" styleClass="section-smallTitle"
		rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.updateAction}">
		<f:param value="#{autoLpController.autoListPrincipale.autoLp.libelle}"/>
	</e:text>
	<t:panelGroup>
		<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.addAction}"
			action="#{autoLpController.add}"/>
		<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.updateAction}"
			action="#{autoLpController.update}"/>
		<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
			action="#{autoLpController.reset}"/>
	</t:panelGroup>
</e:panelGrid>


<t:panelGrid columns="4" styleClass="displayInfo">
	<h:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
			styleClass="form-field-label-inHeader-validator" for="libelle"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.addAction}"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
			styleClass="form-field-label-inHeader-validator" for="libelleText"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.updateAction}"/>
	</h:panelGroup>
	<h:panelGroup>
		<e:inputText id="libelle" size="30" value="#{autoLpController.autoListPrincipale.autoLp.libelle}"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.addAction}"/>
		<e:text id="libelleText" value="#{autoLpController.autoListPrincipale.autoLp.libelle}"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.updateAction}"/>
	</h:panelGroup>
	
	
	
	
	<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
			styleClass="form-field-label-inHeader-validator" for="inUse"/>
	<t:selectBooleanCheckbox id="inUse" value="#{autoLpController.autoListPrincipale.autoLp.temoinEnService}"/>
	
	<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL']}"
			styleClass="form-field-label-inHeader-validator" for="mail"/>
	<e:inputText id="mail" size="30" value="#{autoLpController.autoListPrincipale.autoLp.mail}"/>
	
	
	
	
	<e:outputLabel value="#{msgs['FIELD_LABEL.CHOIX_CGE_COMMISSION_VET']}"
			styleClass="form-field-label-inHeader-validator" for="choixCgeCommission"/>
	<t:panelGroup>
		<e:selectOneRadio id="choixCgeCommission" styleClass="oneRadio"
			onclick="javascript:{radioOnchange(this, '#{autoLpController.choix}', 'seeAutoLpForm:selectChoix');}"
			value="#{autoLpController.choix}">
		    <f:selectItems value="#{autoLpController.allChoix}"/>
		</e:selectOneRadio>
		<e:commandButton id="selectChoix"/>
	</t:panelGroup>
	
	<h:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_CGE']}"
			styleClass="form-field-label-inHeader-validator" for="listCge"
			rendered="#{autoLpController.choixCge}"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_COMMISSION']}"
			styleClass="form-field-label-inHeader-validator" for="listCommission"
			rendered="#{autoLpController.choixCommission}"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_VET']}"
			styleClass="form-field-label-inHeader-validator" for="listVet"
			rendered="#{autoLpController.choixVet}"/>
	</h:panelGroup>
	<h:panelGroup>
		<t:selectOneMenu id="listCge"
			styleClass="oneMenu"
			rendered="#{autoLpController.choixCge}"
			value="#{autoLpController.codeCge}">
			<f:selectItems value="#{autoLpController.allCge}"/>
		</t:selectOneMenu>
		
		<t:panelGrid columns="3" id="listCommission" 
			rendered="#{autoLpController.choixCommission}">
			<t:selectManyMenu styleClass="manyMenu"
				value="#{autoLpController.selectCommADI}">
				<f:selectItems value="#{autoLpController.allCommItems}"/>
			</t:selectManyMenu>
			<t:panelGrid columns="1">
				<e:commandButton image="/media/images/fleche_gauche.gif"
					styleClass="icone"
					action="#{autoLpController.suppCommItems}"/>
				<e:commandButton image="/media/images/fleche_droite.gif"
					styleClass="icone"
					action="#{autoLpController.ajouCommItems}"/>
			</t:panelGrid>
			<t:selectManyMenu styleClass="manyMenu"
				value="#{autoLpController.selectCommDI}">
				<f:selectItems value="#{autoLpController.commItems}"/>
			</t:selectManyMenu>
		</t:panelGrid>
		
		<t:panelGrid columns="3" id="listVet" 
			rendered="#{autoLpController.choixVet}">
			<t:selectManyMenu styleClass="manyMenu"
				value="#{autoLpController.selectVetADI}">
				<f:selectItems value="#{autoLpController.allVetItems}"/>
			</t:selectManyMenu>
			<t:panelGrid columns="1">
				<e:commandButton image="/media/images/fleche_gauche.gif"
					styleClass="icone"
					action="#{autoLpController.suppVetItems}"/>
				<e:commandButton image="/media/images/fleche_droite.gif"
					styleClass="icone"
					action="#{autoLpController.ajouVetItems}"/>
			</t:panelGrid>
			<t:selectManyMenu styleClass="manyMenu"
				value="#{autoLpController.selectVetDI}">
				<f:selectItems value="#{autoLpController.vetItems}"/>
			</t:selectManyMenu>
		</t:panelGrid>
	</h:panelGroup>
	
	
	
	<e:outputLabel value="#{msgs['FIELD_LABEL.TYPE_DECISION_LP']}"
			styleClass="form-field-label-inHeader-validator" for="typeDecLp"/>
	<h:panelGroup>
		<t:outputText id="textTypeDecLp"
			rendered="#{autoLpController.isOnTypeDecLp}"
			value="#{autoLpController.selectTypeDeLpILibelle}">
		</t:outputText>
		
		<t:selectOneMenu id="typeDecLp"
			rendered="#{!autoLpController.isOnTypeDecLp}"
			value="#{autoLpController.typeDecisionDeLP}">
			<f:selectItems value="#{autoLpController.allTypeDecisionIA}"/>
		</t:selectOneMenu>
	</h:panelGroup>
	
	<h:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.TYPE_DECISION_LC']}"
			styleClass="form-field-label-inHeader-validator" for="typeDecLcModif"
			rendered="#{autoLpController.actionListLc.whatAction == autoLpController.actionEnum.emptyAction}"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.TYPE_DECISION_LC']}"
			styleClass="form-field-label-inHeader-validator" for="typeDecLcModif"
			rendered="#{autoLpController.actionListLc.whatAction == autoLpController.actionEnum.addAction}"/>
	</h:panelGroup>
	<h:panelGroup>
		<t:outputText id="textTypeDecLc"
			rendered="#{autoLpController.isOnTypeDecLc}"
			value="#{autoLpController.selectTypeDeLcILibelle}">
		</t:outputText>
		
		<h:panelGroup
			rendered="#{!autoLpController.isOnTypeDecLc}">
			<t:panelGrid columns="3" id="typeDecLcPosition" styleClass="displayInfo"
				rendered="#{autoLpController.actionListLc.whatAction == autoLpController.actionEnum.emptyAction}">
				<t:panelGrid columns="1">
					<e:commandButton image="/media/images/en_haut.gif" styleClass="icone"
						action="#{autoLpController.enHaut}"/>
					<e:commandButton image="/media/images/monter.gif" styleClass="icone"
						action="#{autoLpController.monter}"/>
					<e:commandButton image="/media/images/descendre.gif" styleClass="icone"
						action="#{autoLpController.descendre}"/>
					<e:commandButton image="/media/images/en_bas.gif" styleClass="icone"
						action="#{autoLpController.enBas}"/>
				</t:panelGrid>
				<t:selectOneListbox styleClass="manyMenu"
					value="#{autoLpController.selectTypeDecPosition}">
					<f:selectItems value="#{autoLpController.typeDecItems}"/>
				</t:selectOneListbox>
				<t:panelGroup>
					<e:commandButton image="/media/images/add.png" styleClass="icone"
						rendered="#{empty autoLpController.typeDecItems}"
						action="#{autoLpController.goAddListLc}"/>
					<e:commandButton image="/media/images/update.png" styleClass="icone"
						rendered="#{not empty autoLpController.typeDecItems}"
						action="#{autoLpController.goAddListLc}"/>
				</t:panelGroup>
			</t:panelGrid>
			
			<t:panelGrid columns="4" id="typeDecLcModif" styleClass="displayInfo"
				rendered="#{autoLpController.actionListLc.whatAction == autoLpController.actionEnum.addAction}">
				<t:selectManyMenu styleClass="manyMenu"
					value="#{autoLpController.selectTypeDecADI}">
					<f:selectItems value="#{autoLpController.allTypeDecLcItems}"/>
				</t:selectManyMenu>
				<t:panelGrid columns="1">
					<e:commandButton image="/media/images/fleche_gauche.gif"
						styleClass="icone"
						action="#{autoLpController.suppTypeDecItems}"/>
					<e:commandButton image="/media/images/fleche_droite.gif"
						styleClass="icone"
						action="#{autoLpController.ajouTypeDecItems}"/>
				</t:panelGrid>
				<t:selectManyMenu styleClass="manyMenu"
					value="#{autoLpController.selectTypeDecDI}">
					<f:selectItems value="#{autoLpController.typeDecItems}"/>
				</t:selectManyMenu>
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						action="#{autoLpController.goSeeListLc}"/>
				</t:panelGroup>
			</t:panelGrid>
		</h:panelGroup>
	</h:panelGroup>
</t:panelGrid>
