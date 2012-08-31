<%@include file="../../../_include/_include.jsp"%>
<t:div
	rendered="#{pjController.currentCmiPojo != null 
						&& pjController.currentCmiPojo.state.displayPJ}">
	<h:form id="listePieceManquante">
		<t:div styleClass="blockButton" style="text-align:right;">
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					rendered="#{managedAccess.updateAuthorized
								|| managedAccess.addAuthorized}"
					action="#{pjController.saveMissingPiecePrincipal}" />
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					action="#{pjController.searchStudents}" immediate="true" />
			</t:panelGroup>
		</t:div>
		<e:dataTable var="missingPiece" id="pjData"
			value="#{pjController.mpPojoSelected.piecesForCmi[pjController.currentCmiPojo.commission]}"
			styleClass="paginatorData" alternateColors="true">
			<t:column width="2%">
				<t:popup styleClass="containerPopup" style="width:500px;"
					closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
					displayAtDistanceX="201" displayAtDistanceY="0">
					<t:graphicImage url="/media/images/informationSmall.png"
						style="cursor:pointer;" id="infoAnneeUni" />
					<f:facet name="popup">
						<h:panelGroup>
							<h:panelGrid columns="1">
								<t:outputText styleClass="titrePopupAide"
									value="#{msgs['FIELD_LABEL.LIBELLE']}" />
								<t:outputText styleClass="libellesAide"
												value="#{missingPiece.piece.libelle}" />
							</h:panelGrid>
						</h:panelGroup>
					</f:facet>
				</t:popup>
			</t:column>
			<t:column width="68%">
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
				</f:facet>
				<e:text value="#{missingPiece.piece.libelle}" />
			</t:column>
			<t:column width="28%">
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.COMMENTAIRE']}" />
				</f:facet>
				<e:inputText value="#{missingPiece.libelle}" size="40" maxlength="500"/>
			</t:column>
			<t:column width="2%">
				<f:facet name="header">
					<t:selectBooleanCheckbox value="#{pjController.allChecked}"
						onclick="checkAllInElement('listePieceManquante:pjData' ,this.checked)" />
				</f:facet>
				<jdt:multipleRowsSelector
					selectionList="#{pjController.missingPiece}" />
			</t:column>
		</e:dataTable>
	</h:form>
</t:div>


