<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">

	<%@include file="../../_include/_header.jsp"%>

	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>

	<e:section value="#{msgs['STAT.MANAGED_PARAM']}" />
	<t:div styleClass="messageForUser">
		<e:messages />
	</t:div>

	<h:form id="seeParamStatForm" styleClass="div_subSection">
		<t:div id="parametreStat" styleClass="div_body">
			<t:div styleClass="blockButton">
				<e:commandButton value="#{msgs['_.BUTTON.RESULT_STAT']}"
					immediate="true"
					rendered="#{not empty parametreStatController.listAbscisseCoordonne &&
							not empty parametreStatController.listOrdonneCoordonne &&
							not empty parametreStatController.campagneItems}"
					action="#{seeStatController.goSeeResulStat}" />

				<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction != parametreStatController.addUpdateCoordonne.addAction
								&& parametreStatController.addUpdateCoordonne.whatAction != parametreStatController.addUpdateCoordonne.updateAction}"
					action="#{managedAccess.goDisplayFunction}" />

				<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction 
								&& parametreStatController.actionEnum.whatAction == parametreStatController.actionEnum.emptyAction }"
					action="#{managedAccess.goDisplayFunction}">
				</e:commandButton>

				<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
					rendered="#{parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.addAction ||
							(parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction 
					 		&&  parametreStatController.actionEnum.whatAction != parametreStatController.actionEnum.emptyAction )}"
					action="#{parametreStatController.clearObjAbscisse}">
					<t:updateActionListener
						value="#{parametreStatController.actionEnum.emptyAction}"
						property="#{parametreStatController.actionEnum.whatAction}" />
					<t:updateActionListener
						value="#{parametreStatController.actionEnum.emptyAction}"
						property="#{parametreStatController.addUpdateCoordonne.whatAction}" />
				</e:commandButton>
			</t:div>

			<t:panelGroup id="orderedDelete"
				rendered="#{parametreStatController.actionEnum.whatAction == parametreStatController.actionEnum.paramOrdonne
								&& parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.deleteAction}">
				<%@include file="_delete/_deleteParamStatOrdonne.jsp"%>
			</t:panelGroup>

			<t:panelGroup id="abscissaDelete"
				rendered="#{parametreStatController.actionEnum.whatAction == parametreStatController.actionEnum.paramAbscisse
								&& parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.deleteAction}">
				<%@include file="_delete/_deleteParamStatAbscisse.jsp"%>
			</t:panelGroup>

			<t:panelGrid columns="3" id="coordinatedSelected"
				styleClass="displayInfo"
				rendered="#{parametreStatController.actionEnum.whatAction == parametreStatController.actionEnum.emptyAction}">
				<e:outputLabel value="#{msgs['FIELD_LABEL.ORDONNE']}"
					styleClass="formHeader" for="ordered" />

				<e:outputLabel value="#{msgs['FIELD_LABEL.ABSCISSE']}"
					styleClass="formHeader" for="abscissa" />

				<e:outputLabel value="#{msgs['FIELD_LABEL.CAMPAGNES']}"
					styleClass="formHeader" for="campagne" />

				<t:panelGrid columns="3" id="ordered">
					<t:panelGrid columns="1">
						<e:commandButton image="/media/images/en_haut.gif"
							styleClass="icone"
							action="#{parametreStatController.enHautOrdonne}" />
						<e:commandButton image="/media/images/monter.gif"
							styleClass="icone"
							action="#{parametreStatController.monterOrdonne}" />
						<e:commandButton image="/media/images/descendre.gif"
							styleClass="icone"
							action="#{parametreStatController.descendreOrdonne}" />
						<e:commandButton image="/media/images/en_bas.gif"
							styleClass="icone"
							action="#{parametreStatController.enBasOrdonne}" />
					</t:panelGrid>
					<t:selectOneListbox styleClass="manyMenu"
						value="#{parametreStatController.selectOrdonneCoordonne}">
						<f:selectItems
							value="#{parametreStatController.listOrdonneCoordonneItems}" />
					</t:selectOneListbox>
					<t:panelGroup>
						<e:commandButton image="/media/images/add.png" styleClass="icone">
							<t:updateActionListener
								value="#{parametreStatController.actionEnum.paramOrdonne}"
								property="#{parametreStatController.actionEnum.whatAction}" />
							<t:updateActionListener
								value="#{parametreStatController.addUpdateCoordonne.addAction}"
								property="#{parametreStatController.addUpdateCoordonne.whatAction}" />
						</e:commandButton>
						<e:commandButton image="/media/images/update.png"
							styleClass="icone"
							rendered="#{not empty parametreStatController.listOrdonneCoordonne}"
							action="#{parametreStatController.changeSelectCoordonneOrdonneUpdate}" />
						<e:commandButton image="/media/images/cancel.png"
							styleClass="icone"
							rendered="#{not empty parametreStatController.listOrdonneCoordonne}"
							action="#{parametreStatController.changeSelectCoordonneOrdonne}">
							<t:updateActionListener
								value="#{parametreStatController.actionEnum.paramOrdonne}"
								property="#{parametreStatController.actionEnum.whatAction}" />
							<t:updateActionListener
								value="#{parametreStatController.addUpdateCoordonne.deleteAction}"
								property="#{parametreStatController.addUpdateCoordonne.whatAction}" />
						</e:commandButton>
					</t:panelGroup>
				</t:panelGrid>

				<t:panelGrid columns="3" id="abscissa">
					<t:panelGrid columns="1">
						<e:commandButton image="/media/images/en_haut.gif"
							styleClass="icone"
							action="#{parametreStatController.enHautAbscisse}" />
						<e:commandButton image="/media/images/monter.gif"
							styleClass="icone"
							action="#{parametreStatController.monterAbscisse}" />
						<e:commandButton image="/media/images/descendre.gif"
							styleClass="icone"
							action="#{parametreStatController.descendreAbscisse}" />
						<e:commandButton image="/media/images/en_bas.gif"
							styleClass="icone"
							action="#{parametreStatController.enBasAbscisse}" />
					</t:panelGrid>
					<t:selectOneListbox styleClass="manyMenu"
						value="#{parametreStatController.selectAbscisseCoordonne}">
						<f:selectItems
							value="#{parametreStatController.listAbscisseCoordonneItems}" />
					</t:selectOneListbox>
					<t:panelGroup>
						<e:commandButton image="/media/images/add.png" styleClass="icone">
							<t:updateActionListener
								value="#{parametreStatController.actionEnum.paramAbscisse}"
								property="#{parametreStatController.actionEnum.whatAction}" />
							<t:updateActionListener
								value="#{parametreStatController.addUpdateCoordonne.addAction}"
								property="#{parametreStatController.addUpdateCoordonne.whatAction}" />
						</e:commandButton>
						<e:commandButton image="/media/images/update.png"
							styleClass="icone"
							rendered="#{not empty parametreStatController.listAbscisseCoordonne}"
							action="#{parametreStatController.changeSelectCoordonneAbscisseUpdate}" />
						<e:commandButton image="/media/images/cancel.png"
							styleClass="icone"
							rendered="#{not empty parametreStatController.listAbscisseCoordonne}"
							action="#{parametreStatController.changeSelectCoordonneAbscisse}">
							<t:updateActionListener
								value="#{parametreStatController.actionEnum.paramAbscisse}"
								property="#{parametreStatController.actionEnum.whatAction}" />
							<t:updateActionListener
								value="#{parametreStatController.addUpdateCoordonne.deleteAction}"
								property="#{parametreStatController.addUpdateCoordonne.whatAction}" />
						</e:commandButton>
					</t:panelGroup>
				</t:panelGrid>

				<t:panelGrid columns="3" id="campagne">
					<t:selectManyMenu styleClass="manyMenu"
						value="#{parametreStatController.selectCampagneAdd}">
						<f:selectItems value="#{parametreStatController.allCampagneItems}" />
					</t:selectManyMenu>
					<t:panelGrid columns="1">
						<e:commandButton image="/media/images/fleche_gauche.gif"
							styleClass="icone"
							action="#{parametreStatController.delCampagneItems}" />
						<e:commandButton image="/media/images/fleche_droite.gif"
							styleClass="icone"
							action="#{parametreStatController.addCampagneItems}" />
					</t:panelGrid>
					<t:selectManyMenu styleClass="manyMenu"
						value="#{parametreStatController.selectCampagneDel}">
						<f:selectItems value="#{parametreStatController.campagneItems}" />
					</t:selectManyMenu>
				</t:panelGrid>
			</t:panelGrid>

			<t:panelGroup id="orderedSelected"
				rendered="#{parametreStatController.actionEnum.whatAction == parametreStatController.actionEnum.paramOrdonne
								&& (parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.addAction
									|| parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction)}">
				<%@include file="_add/_enterParamStatOrdonne.jsp"%>
			</t:panelGroup>

			<t:panelGroup id="abscissaSelected"
				rendered="#{parametreStatController.actionEnum.whatAction == parametreStatController.actionEnum.paramAbscisse
								&& (parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.addAction
									|| parametreStatController.addUpdateCoordonne.whatAction == parametreStatController.addUpdateCoordonne.updateAction)}">
				<%@include file="_add/_enterParamStatAbscisse.jsp"%>
			</t:panelGroup>
		</t:div>
	</h:form>

	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>

	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
	hideElement('seeParamStatForm:submitTypeOrdonne');
	hideElement('seeParamStatForm:submitTypeAbscisse');
</script>
</e:page>
