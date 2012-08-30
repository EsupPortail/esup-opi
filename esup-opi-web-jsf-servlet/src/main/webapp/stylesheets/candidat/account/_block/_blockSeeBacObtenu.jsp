<%@include file="../../../_include/_include.jsp"%>
<t:dataList var="indBacPojo" value="#{indBacController.indBacPojos}"
	rowIndexVar="row">
	<t:div styleClass="blockForm">
		<t:div style="width:100%;">
			<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
				styleClass="tableJustWidthMax">
				<t:panelGroup>
					<e:text value="#{indBacPojo.indBac.dateObtention}"
						styleClass="section-smallTitle" />
				</t:panelGroup>
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
						action="#{indBacController.goUpdateIndBac}"
						rendered="#{!indBacPojo.indBac.temoinFromApogee 
							&& sessionController.currentInd.asRightsToUpdate}">
						<t:updateActionListener value="#{indBacPojo.indBac}"
							property="#{indBacController.indBac}" />
						<t:updateActionListener
							value="#{indBacController.actionEnum.updateAction}"
							property="#{indBacController.actionEnum.whatAction}" />
					</e:commandButton>
					<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
						immediate="true" 
						rendered="#{sessionController.whoIsConnectInPortlet != 'individu.not_exist'}"
						action="#{accueilController.goWelcomeCandidat}" />

				</t:panelGroup>
			</e:panelGrid>
		</t:div>
		<e:panelGrid styleClass="tableWidthMax" columns="2"
			columnClasses="colUnQuart,colTroisQuart">
			<t:panelGroup rendered="#{indBacPojo.indBac.codPay != null}">
				<e:outputLabel value="#{msgs['ADRESS.PAY']}" for="paysBac" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup rendered="#{indBacPojo.indBac.codPay != null}">
				<e:text value="#{indBacPojo.pays.libPay}" />
			</t:panelGroup>

			<t:panelGroup rendered="#{indBacPojo.departement != null}">
				<e:outputLabel value="#{msgs['FIELD_LABEL.DEPARTEMENT']}"
					for="depBac" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup rendered="#{indBacPojo.departement != null}">
				<e:text value="#{indBacPojo.departement.libDep}" />
			</t:panelGroup>

			<t:panelGroup rendered="#{indBacPojo.indBac.codCom != null}">
				<e:outputLabel value="#{msgs['ADRESS.VIL']}" for="commune" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup rendered="#{indBacPojo.indBac.codCom != null}">
				<e:text value="#{indBacPojo.commune.libCom}" />
			</t:panelGroup>

			<t:panelGroup rendered="#{indBacPojo.indBac.codEtb != null">
				<e:outputLabel value="#{msgs['ETABLISSEMENT']}" for="etabBac" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup rendered="#{indBacPojo.indBac.codEtb != null">
				<e:text value="#{indBacPojo.etablissement.libEtb}" />
			</t:panelGroup>

			<t:panelGroup>
				<e:outputLabel value="#{msgs['INDIVIDU.BAC.DAA_OBT']}" for="daaObt" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:text value="#{indBacPojo.indBac.dateObtention}" />
			</t:panelGroup>

			<t:panelGroup>
				<e:outputLabel value="#{msgs['INDIVIDU.CURSUS.SERIE_BAC']}"
					for="serieBac" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:text value="#{indBacPojo.bacOuxEqu.libBac}" />

			<e:outputLabel value="#{msgs['INDIVIDU.CURSUS.MENTION']}"
				for="mention" />
			<e:text value="#{indBacPojo.mentionNivBac.libMnb}" />
			<t:panelGroup />
			<t:panelGroup />

		</e:panelGrid>
	</t:div>

	<t:htmlTag value="br" />
	<t:htmlTag value="br" />
	<t:div styleClass="blockImportant"
		rendered="#{indBacPojo.indBac.temoinFromApogee && row == 0}">
		<e:panelGrid columns="2" columnClasses="image,text">
			<t:graphicImage url="/media/images/important.png" />
			<t:outputText
				value="#{msgs['ERROR.HELP']}" />
		</e:panelGrid>
	</t:div>
</t:dataList>
<t:htmlTag value="br" />
