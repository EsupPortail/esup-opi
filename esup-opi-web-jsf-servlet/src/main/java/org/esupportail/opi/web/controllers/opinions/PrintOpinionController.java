package org.esupportail.opi.web.controllers.opinions;

import fj.Effect;
import fj.F;
import fj.P1;
import fj.control.parallel.Strategy;
import fj.data.Conversions;
import fj.data.Option;
import fj.data.Stream;
import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.InscriptionAdm;
import org.esupportail.opi.domain.beans.parameters.Refused;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.services.export.CastorService;
import org.esupportail.opi.services.export.ISerializationService;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.*;
import org.esupportail.opi.web.beans.utils.ExportUtils;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.PDFUtils;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.controllers.references.CommissionController;
import org.esupportail.opi.web.controllers.user.CursusController;
import org.esupportail.opi.web.controllers.user.IndividuController;
import org.esupportail.opi.web.utils.MiscUtils;
import org.esupportail.opi.web.utils.fj.parallel.ParallelModule;
import org.esupportail.opi.web.utils.io.SuperCSV;
import org.esupportail.opi.web.utils.paginator.LazyDataModel;
import org.esupportail.opi.web.utils.paginator.PaginationFunctions;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;
import org.supercsv.io.ICsvBeanWriter;

import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

import static fj.P.p;
import static fj.data.Array.array;
import static fj.data.Array.iterableArray;
import static fj.data.IterableW.wrap;
import static fj.data.Option.fromNull;
import static fj.data.Option.some;
import static fj.data.Stream.iterableStream;
import static fj.data.Stream.join;
import static fj.function.Booleans.not;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.Transfert;
import static org.esupportail.opi.utils.Constantes.ADR_FIX;
import static org.esupportail.opi.utils.Constantes.DATE_FORMAT;
import static org.esupportail.opi.web.beans.utils.Utilitaires.convertDateToString;
import static org.esupportail.opi.web.utils.fj.Conversions.individuToPojo;
import static org.esupportail.opi.web.utils.fj.Functions.getRICode;
import static org.esupportail.opi.web.utils.fj.Predicates.hasTypeTrt;
import static org.esupportail.opi.web.utils.io.SuperCSV.*;
import static org.esupportail.opi.web.utils.paginator.LazyDataModel.lazyModel;


public class PrintOpinionController extends AbstractContextAwareController {

    private static final long serialVersionUID = 7174653291470562703L;

    private static final List<String> HEADER_CVS =
            new ArrayList<String>() {
                private static final long serialVersionUID = 4451087010675988608L;
                {
                    add("Commission");
                    add("Num_Dos_OPI");
                    add("Nom_Patrony");
                    add("Prenom");
                    add("Date_Naiss");
                    add("Code_clef_INE");
                    add("Adresse_1");
                    add("Adresse_2");
                    add("Adresse_3");
                    add("Cedex");
                    add("Code_Postal");
                    add("Lib_Commune");
                    add("Lib_Pays");
                    add("Telephone_fixe");
                    add("Mail");
                    add("Bac");
                    add("Dernier_Etab_Cur");
                    add("Dernier_Etab_Etb");
                    add("Dernier_Etab_Result_Ext");
                    add("Date_depot_voeu");
                    add("Type_Traitement");
                    add("Voeu_Lib_Vet");
                    add("Etat_Voeu");
                    add("Avis_Result_Lib");
                    add("Rang");
                    add("Avis_Motivation_Commentaire");
                    add("Avis_Result_Code");
                    add("Avis_Result_Code_Apogee");
                    add("Avis_temoin_validation");
                    add("Avis_date_validation");
                }
            };

    private final Logger log = new LoggerImpl(getClass());

    /**
     * Champs dispo pour l'export.
     */
    private List<String> champsDispos;
    /**
     * liste des champs selectionnes.
     */
    private String[] champsChoisis;

    /**
     * The result (of type Final or not final)
     * *selectionned or not by the gestionnaire.
     */
    private Object[] resultSelected;

    /**
     * List of commissions selected.
     */
    private Object[] commissionsSelected;
    /**
     * Has true if all Commission are selected.
     * Default value = false
     */
    private Boolean allChecked;

    /**
     * List of individus for a commission and avis positionned by gestionnaire.
     */
    private List<IndividuPojo> lesIndividus;

    /**
     * Data for pdf generation.
     */
    private Map<Commission, List<NotificationOpinion>> pdfData;

    private IndividuController individuController;

    private CommissionController commissionController;

    private ExportFormOrbeonController exportFormOrbeonController;

    private CursusController cursusController;

    private ActionEnum actionEnum;

    private InscriptionAdm inscriptionAdm;

    private Boolean printOnlyDef;

    private Refused refused;

    private ISerializationService castorService;

    private SmtpService smtpService;

    private IndividuPojo individuPojoSelected;

    private IndRechPojo indRechPojo = new IndRechPojo();

    private final LazyDataModel<IndividuPojo> indPojoLDM = lazyModel(
            PaginationFunctions.getData(
                    new P1<SessionController>() {
                        public SessionController _1() {
                            return getSessionController();
                        }
                    },
                    new P1<DomainService>() {
                        public DomainService _1() {
                            return getDomainService();
                        }
                    },
                    new P1<DomainApoService>() {
                        public DomainApoService _1() {
                            return getDomainApoService();
                        }
                    },
                    new P1<ParameterService>() {
                        public ParameterService _1() {
                            return getParameterService();
                        }
                    },
                    new P1<IndRechPojo>() {
                        public IndRechPojo _1() {
                            return indRechPojo;
                        }
                    }),
            PaginationFunctions.findByRowKey)
            .map(individuToPojo(
                    new P1<DomainApoService>() {
                        public DomainApoService _1() {
                            return getDomainApoService();
                        }
                    },
                    new P1<ParameterService>() {
                        public ParameterService _1() {
                            return getParameterService();
                        }
                    }));


    private boolean renderTable;

    public PrintOpinionController() {
        super();
    }

    @Override
    public void reset() {
        commissionController.reset();
        this.resultSelected = new Object[0];
        this.commissionsSelected = new Object[0];
        this.lesIndividus = new ArrayList<>();
        champsDispos = new ArrayList<>();
        List<String> lChampschoisis = new ArrayList<>();
        for (String champs : HEADER_CVS) {
            champsDispos.add(champs);
            lChampschoisis.add(champs);
        }
        champsChoisis = lChampschoisis.toArray(new String[lChampschoisis.size()]);
        this.allChecked = false;
        this.pdfData = new HashMap<>();
        this.actionEnum = new ActionEnum();
        renderTable = false;
    }

    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        Assert.notNull(this.castorService,
                "property castorService of class " + this.getClass().getName()
                        + " can not be null");
        Assert.notNull(this.commissionController,
                "property commissionController of class " + this.getClass().getName()
                        + " can not be null");
        Assert.notNull(this.exportFormOrbeonController,
                "property exportFormOrbeonController of class " + this.getClass().getName()
                        + " can not be null");
        reset();
    }

    /**
     * Callback for the print of opinions.
     *
     * @return String
     */
    public String goPrintOpinions() {
        reset();
        return NavigationRulesConst.DISPLAY_PRINT_OPINIONS;
    }

    /**
     * Callback for the print of TR opinions.
     *
     * @return String
     */
    public String goPrintTROpinions() {
        reset();
        return NavigationRulesConst.DISPLAY_PRINT_TR_OPINIONS;
    }

    /**
     * Find student.
     * call in printOpinions.jsp
     */
    public void seeCandidats() {
        makeAllIndividus(some(indRechPojo.getSelectValid()));
    }

    /**
     * Print pdf after set the list of students.
     * call in printOpinions.jsp
     */
    public void printPDFValidation() {
        makeAllIndividus(some(indRechPojo.getSelectValid()));
        makePDFValidation();
        this.lesIndividus = new ArrayList<>();
    }

    /**
     * Make pdf after set the list of students.
     * call in printOpinions.jsp
     */
    public void makeCsvValidationNew() {
        final String fileNamePrefix = "exportAvis";
        final String fileNameSuffix = ".csv";
        // list of indivius from the commission selected
        // with an opinion not validate
        Integer idCmi = indRechPojo.getIdCmi();
        if (idCmi != null) {
            Commission cmi = retrieveOSIVCommission(idCmi, null);
            this.commissionController.setCommission(cmi);
            Boolean selectValid = indRechPojo.getSelectValid();
            writeCSVListes(
                    cmi,
                    getIndividus(cmi, some(selectValid), not(hasTypeTrt(Transfert))),
                    fileNamePrefix,
                    fileNameSuffix);
        }
    }

    /**
     * Make pdf after set the list of students.
     * call in printOpinions.jsp
     */
    public void makeCsvFormulaire() {
        Option<Integer> idComOpt = fromNull(indRechPojo.getIdCmi());
        List<RegimeInscription> listRI = new ArrayList<>(indRechPojo.getListeRI());

        for (Integer idCom : idComOpt)
            exportFormOrbeonController.makeCsvFormulaire(
                    retrieveOSIVCommission(idCom, null),
                    listRI);

        if (idComOpt.isNone())
            addErrorMessage(null, "ERROR.PRINT.COMMISSION_NULL");

        if (listRI.isEmpty())
            addErrorMessage(null, "ERROR.PRINT.LIST_RI_NULL");
    }

    /**
     * Make csv after see the list of students.
     * call in seeNotEtudiant.jsp
     */
    public void makeCsvInSeeEtuVal() {
        final String fileNamePrefix = "exportAvis_";
        final String fileNameSuffix = ".csv";
        final Commission commission = commissionController.getCommission();

        writeCSVListes(
                commission,
                getIndividus(
                        retrieveOSIVCommission(commission.getId(), commission.getCode()),
                        Option.<Boolean>none(),
                        fj.Function.<IndVoeuPojo, Boolean>constant(true)),
                fileNamePrefix,
                fileNameSuffix);
    }

    /**
     * print pdf of notification for the student selected.
     * call in printOpinions.jsp
     */
    public void printOneNotification() {
        this.pdfData.clear();

        List<IndividuPojo> individus = new ArrayList<>();
        individus.add(individuPojoSelected);

        Commission com = retrieveOSIVCommission(indRechPojo.getIdCmi(), null);

        makePdfData(individus, com);

        if (!this.pdfData.isEmpty()) {
            printOnlyDef = false;
            notificationPdfGeneration();
        } else {
            addInfoMessage(null, "INFO.PRINT.NO_NOTIFICATION");
        }
    }

    /**
     * print pdf of notifications for the student of the commission selected.
     * call in printOpinions.jsp
     */
    public void printPDFAllNotifications() {
        this.pdfData.clear();
        makeAllIndividus(some(indRechPojo.getSelectValid()));

        Commission com = retrieveOSIVCommission(indRechPojo.getIdCmi(), null);

        makePdfData(lesIndividus, com);

        if (!this.pdfData.isEmpty()) {
            printOnlyDef = false;
            notificationPdfGeneration();
        } else {
            addInfoMessage(null, "INFO.PRINT.NO_NOTIFICATION");
        }
        this.lesIndividus = new ArrayList<>();
    }

    public void generateCSVListesPreparatoire() {
        writeCSVListes("listePrepa", ".csv", not(hasTypeTrt(Transfert)));
    }

    public void generateCSVListesTransfert() {
        writeCSVListes("listeTransfert", ".csv", hasTypeTrt(Transfert));
    }

    private void writeCSVListes(String fileNamePrefix, String fileNameSuffix, F<IndVoeuPojo, Boolean> voeuFilter) {
        final Commission commission = commissionController.getCommission();
        writeCSVListes(
                commission,
                getIndividus(
                        retrieveOSIVCommission(commission.getId(), commission.getCode()),
                        some(true),
                        voeuFilter),
                fileNamePrefix,
                fileNameSuffix);
    }

    private void writeCSVListes(final Commission commission,
                                final Stream<IndividuPojo> individus,
                                final String fileNamePrefix,
                                final String fileNameSuffix) {
        // seems dumb but we prefer to access a copy of the session variable in case of concurrent accesses
        final String[] champs =
                (champsChoisis == null) ? HEADER_CVS.toArray(new String[HEADER_CVS.size()]) : champsChoisis;

        final User currentUser = getSessionController().getCurrentUser();
        final I18nService i18n = getI18nService();
        final String prefix = fileNamePrefix + "_" + commission.getCode() + "_";
        // a helper class to get a handle on the temp Path within the try-with-resources block
        class FileHolder implements Closeable {
            private Path path;
            public void close() throws IOException {}
            public Path getFile() throws IOException {
                if (path == null) path = Files.createTempFile(prefix, fileNameSuffix);
                return path;
            }
        }

        try (final FileHolder holder = new FileHolder();
             final SuperCSV<ICsvBeanWriter> csv = superCSV(holder.getFile(), champs)) {
            forEach(individus,
                    new IOF<IndividuPojo, IOUnit>() {
                        public IOUnit fio(IndividuPojo ip) throws IOException {
                            return forEach(indPojoToLignes(ip, commission), new IOF<LigneCSV, IOUnit>() {
                                public IOUnit fio(LigneCSV ligne) throws IOException {
                                    return csv.map(SuperCSV.<LigneCSV>write_().fio(p(ligne, champs))).run();
                                }
                            });
                        }
                    });
            Utilitaires.sendEmail.f(smtpService, false).e(p(
                    new InternetAddress(currentUser.getAdressMail()),
                    i18n.getString("EXPORT.CSV.MAIL.SUBJECT"),
                    "",
                    i18n.getString("EXPORT.CSV.MAIL.BODY"),
                    Arrays.<File>asList(holder.getFile().toFile())
            ));

        } catch (Exception e) {
            log.error(e);
            try {
                Utilitaires.sendEmail.f(smtpService, false).e(p(
                        new InternetAddress(currentUser.getAdressMail()),
                        i18n.getString("EXPORT.CSV.ERROR.MAIL.SUBJECT"),
                        "",
                        i18n.getString("EXPORT.CSV.ERROR.MAIL.BODY"),
                        Collections.<File>emptyList()));

            } catch (AddressException ae) {
                log.error(ae);
            }
        }
    }

    /**
     * @deprecated see {@see #writeCSVListes()} for new implementation
     *             Generate a CSV of the list of student.
     */
    @Deprecated
    public String csvGeneration(final List<IndividuPojo> individus, final Commission commission,final String fileName) {
        if (champsChoisis == null)
            champsChoisis = HEADER_CVS.toArray(new String[HEADER_CVS.size()]);

        List<LigneCSV> listePrepa = new ArrayList<>(
                join(iterableArray(individus).map(new F<IndividuPojo, Stream<LigneCSV>>() {
                    public Stream<LigneCSV> f(IndividuPojo individuPojo) {
                        return indPojoToLignes(individuPojo, commission);
                    }
                }).toStream()).toCollection());
        try {
            ExportUtils.superCsvGenerate(listePrepa, champsChoisis, fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generate a CSV of the list of student.
     *
     * @return String superCsvGeneration
     */
    public Stream<LigneCSV> indPojoToLignes(IndividuPojo ind, Commission commission) {
        Individu i = ind.getIndividu();
        Pays pays = null;
        CommuneDTO commune = null;
        Adresse adresse = i.getAdresses().get(ADR_FIX);
        if (adresse != null) {
            commune = getDomainApoService().getCommune(adresse.getCodCommune(), adresse.getCodBdi());
            pays = getDomainApoService().getPays(adresse.getCodPays());
        }
        List<LigneCSV> lignes = new ArrayList<>();
        for (IndVoeuPojo v : ind.getIndVoeuxPojo()) {
            LigneCSV ligne = new LigneCSV();
            ligne.setCommission(commission.getLibelle());
            ligne.setNum_Dos_OPI(i.getNumDossierOpi());
            ligne.setNom_Patrony(i.getNomPatronymique());
            ligne.setPrenom(i.getPrenom());
            ligne.setDate_Naiss(i.getDateNaissance());

            String ine = ExportUtils.isNotNull(i.getCodeNNE())
                    + ExportUtils.isNotNull(i.getCodeClefNNE());
            ligne.setCode_clef_INE(ExportUtils.isNotNull(ine));

            // adresse
            if (adresse != null) {
                ligne.setAdresse_1(ExportUtils.isNotNull(adresse.getAdr1()));
                ligne.setAdresse_2(ExportUtils.isNotNull(adresse.getAdr2()));
                ligne.setAdresse_3(ExportUtils.isNotNull(adresse.getAdr3()));
                ligne.setCedex(ExportUtils.isNotNull(adresse.getCedex()));
                ligne.setCode_Postal(ExportUtils.isNotNull(adresse.getCodBdi()));
                ligne.setLib_Commune(commune != null ?
                        commune.getLibCommune() :
                        ExportUtils.isNotNull(adresse.getLibComEtr()));
                ligne.setLib_Pays(pays != null ? pays.getLibPay() : "");
                ligne.setTelephone_fixe(ExportUtils.isNotNull(adresse.getPhoneNumber()));
            } else {
                ligne.setAdresse_1("");
                ligne.setAdresse_2("");
                ligne.setAdresse_3("");
                ligne.setCedex("");
                ligne.setCode_Postal("");
                ligne.setLib_Commune("");
                ligne.setLib_Pays("");
                ligne.setTelephone_fixe("");
            }

            ligne.setMail(i.getAdressMail());
            // bac
            boolean hasCodeBac = false;
            for (IndBac iB : i.getIndBac()) {
                BacOuxEqu b = getDomainApoService().getBacOuxEqu(
                        iB.getDateObtention(),
                        ExportUtils.isNotNull(iB.getCodBac()));
                if (b != null) {
                    ligne.setBac(b.getLibBac());
                } else {
                    ligne.setBac(iB.getCodBac());
                }
                hasCodeBac = true;
                break;
            }
            if (!hasCodeBac) {
                ligne.setBac("");
            }
            // dernier cursus
            IndCursusScolPojo d = ind.getDerniereAnneeEtudeCursus();
            if (d != null) {
                ligne.setDernier_Etab_Cur(ExportUtils.isNotNull(d.getLibCur()));
                ligne.setDernier_Etab_Etb(ExportUtils.isNotNull(d.getLibEtb()));
                ligne.setDernier_Etab_Result_Ext(ExportUtils.isNotNull(cursusController.getResultatExt(d)));
            } else {
                ligne.setDernier_Etab_Cur("");
                ligne.setDernier_Etab_Etb("");
                ligne.setDernier_Etab_Result_Ext("");
            }
            // Voeux
            DateFormat sdf = new SimpleDateFormat(Constantes.DATE_HOUR_FORMAT);
            ligne.setDate_depot_voeu(sdf.format(v.getIndVoeu().getDateCreaEnr()));
            ligne.setType_Traitement(ExportUtils.isNotNull(v.getTypeTraitement().code));
            ligne.setVoeu_Lib_Vet(ExportUtils.isNotNull(v.getVrsEtape().getLibWebVet()));
            ligne.setEtat_Voeu(ExportUtils.isNotNull(getI18nService().getString(v.getEtat().getCodeLabel())));

            if (v.getAvisEnService() != null) {

                ligne.setAvis_Result_Lib(ExportUtils.isNotNull(v.getAvisEnService().
                        getResult().getLibelle()));

                if (v.getAvisEnService().getRang() != null) {
                    ligne.setRang(v.getAvisEnService().getRang().toString());
                } else {
                    ligne.setRang("");
                }

                String comm = null;
                if (v.getAvisEnService().getMotivationAvis() != null) {
                    comm = ExportUtils.isNotNull(v.getAvisEnService().getMotivationAvis().getLibelle());
                }
                if (comm != null && StringUtils.hasText(v.getAvisEnService().
                        getCommentaire())) {
                    comm += "/" + v.getAvisEnService().getCommentaire();
                } else {
                    comm += ExportUtils.isNotNull(
                            v.getAvisEnService().getCommentaire());
                }
                ligne.setAvis_Motivation_Commentaire(ExportUtils.isNotNull(comm));

                ligne.setAvis_Result_Code(ExportUtils.isNotNull(v.getAvisEnService().
                        getResult().getCode()));
                ligne.setAvis_Result_Code_Apogee(ExportUtils.isNotNull(v.getAvisEnService().
                        getResult().getCodeApogee()));
                ligne.setAvis_temoin_validation(ExportUtils.isNotNull("" + v.getAvisEnService().
                        getValidation()));

                if (v.getAvisEnService().getValidation()) {
                    ligne.setAvis_date_validation(ExportUtils.isNotNull(
                            convertDateToString(
                                    v.getAvisEnService().
                                            getDateModifEnr(),
                                    DATE_FORMAT)));
                } else {
                    ligne.setAvis_date_validation("");
                }
            } else {
                ligne.setAvis_Result_Lib("");
                ligne.setRang("");
                ligne.setAvis_Motivation_Commentaire("");
                ligne.setAvis_Result_Code("");
                ligne.setAvis_Result_Code_Apogee("");
                ligne.setAvis_temoin_validation("");
                ligne.setAvis_date_validation("");
            }
            lignes.add(ligne);
        }
        return iterableStream(lignes);
    }

    /**
     * @deprecated better use {@see getIndividus()} instead
     *             clear and found the list of IndividuPojo and IndVoeuPjo
     *             filtred by commission and typeDecision selected by the gestionnaire.
     */
    public void lookForIndividusPojo(final Commission laCommission,
                                     final Boolean onlyValidate, final Boolean initCursusPojo, final Boolean excludeTR) {
        throw new UnsupportedOperationException("DEPRECATED !");
    }


    /**
     * @return a {@link Stream} of filtered {@link IndividuPojo}s
     */
    public Stream<IndividuPojo> getIndividus(Commission laCommission,
                                             final Option<Boolean> onlyValidate,
                                             final F<IndVoeuPojo, Boolean> voeuFilter) {
        final HashSet<Integer> listeRI =
                new HashSet<>(wrap(commissionController.getListeRI()).map(getRICode()).toStandardList());
        final F<String, Individu> fetchInd = new F<String, Individu>() {
            public Individu f(String id) {
                return getDomainService().fetchIndById(id, onlyValidate);
            }
        };
        final F<Individu, IndividuPojo> buildPojos =
                individuToPojo(p(getDomainApoService()), p(getParameterService()))
                .andThen(new F<IndividuPojo, IndividuPojo>() {
                    public IndividuPojo f(IndividuPojo ip) {
                        ip.setIndVoeuxPojo(ip.getIndVoeuxPojo().filter(voeuFilter));
                        return ip;
                    }
                });
        final List<String> indsIds = getDomainService().getIndsIds(laCommission, onlyValidate, listeRI);
        final Strategy<IndividuPojo> strat =
                Strategy.<IndividuPojo>executorStrategy(ParallelModule.pool).errorStrategy(new Effect<Error>() {
                    public void e(Error error) {
                        error.printStackTrace();
                    }
                });
        return strat.parMap(fetchInd.andThen(buildPojos), array(indsIds.toArray(new String[indsIds.size()])))
                .map(Conversions.<IndividuPojo>Array_Stream())
                ._1();
    }

    /**
     * Reattachment to hibernate session
     */
    private Commission retrieveOSIVCommission(Integer id, String code) {
        return getParameterService().getCommission(id, code);
    }

    /**
     * Int the commission and make the individuals list.
     */
    private void makeAllIndividus(final Option<Boolean> onlyValidate) {
        // list of indivius from the commission selected
        // with an opinion not validate
        Integer idCmi = indRechPojo.getIdCmi();
        if (idCmi != null) {
            this.commissionController.setCommission(getParameterService().
                    getCommission(idCmi, null));
            lesIndividus = new ArrayList<>(getIndividus(
                    commissionController.getCommission(), onlyValidate, not(hasTypeTrt(Transfert))).toCollection());
        }
    }


    /**
     * Labels of the Results selected by the gestionnaire.
     *
     * @return String
     */
    public String getLabelResultSelected() {
        StringBuilder r = new StringBuilder();
        Boolean first = true;
        for (Object o : this.resultSelected) {
            TypeDecision result = (TypeDecision) o;
            if (first) {
                first = false;
            } else {
                r.append(", ");
            }
            r.append(result.getLibelle());
        }
        return r.toString();
    }

    /**
     * Return the list of Type Opinion of the type selected by the user.
     *
     * @return String
     */
    public String setListTypeOpinions() {
        this.resultSelected = new Object[0];
        return NavigationRulesConst.DISPLAY_VALID_OPINIONS;
    }

    /**
     * Genere le PDF de validation des avis.
     */
    public void makePDFValidation() {
        if (log.isDebugEnabled()) {
            log.debug("entering makePDFValidation()");
        }

        // Map repartissant les IndListePrepaPojo par etape puis par avis
        Map<VersionEtapeDTO, Map<TypeDecision, List<IndListePrepaPojo>>> mapIndListByEtapeAndAvis =
                new TreeMap<VersionEtapeDTO, Map<TypeDecision, List<IndListePrepaPojo>>>(
                        new ComparatorString(VersionEtapeDTO.class));

        // on boucle sur la liste des individus de la commission avec les avis selectionnes
        for (IndividuPojo iP : this.lesIndividus) {
            // hibernate session reattachment
            Individu ind = iP.getIndividu();
            iP.setIndividu(getDomainService().getIndividu(
                    ind.getNumDossierOpi(), ind.getDateNaissance()));

            // initialisation des cursus scolaires
            MiscUtils.initIndCursusScolPojo(iP, getDomainApoService());
            
            // on boucle sur les listes des avis de chaque individu
            for (IndVoeuPojo indVoeuPojo : iP.getIndVoeuxPojo()) {
                Avis unAvis = indVoeuPojo.getAvisEnService();
                if (unAvis != null) {
                    TraitementCmi trtCmi =
                            unAvis.getIndVoeu().getLinkTrtCmiCamp().getTraitementCmi();
                    // on recupere l'etape de l'avis
                    VersionEtapeDTO vDTO = getDomainApoService().getVersionEtape(
                            trtCmi.getVersionEtpOpi().getCodEtp(),
                            trtCmi.getVersionEtpOpi().getCodVrsVet());
                    // on cree l'entree de l'etape si elle n'existe pas
                    if (!mapIndListByEtapeAndAvis.containsKey(vDTO)) {
                        mapIndListByEtapeAndAvis.put(vDTO, new TreeMap<TypeDecision,
                                List<IndListePrepaPojo>>(new ComparatorString(
                                TypeDecision.class)));
                    }

                    // on recupere le type de decision de l'avis
                    TypeDecision typeDec = unAvis.getResult();
                    // on cree l'entree du type de decision si elle n'existe pas
                    if (!mapIndListByEtapeAndAvis.get(vDTO).containsKey(typeDec)) {
                        mapIndListByEtapeAndAvis.get(vDTO).put(typeDec,
                                new ArrayList<IndListePrepaPojo>());
                    }
                    // on cree l'IndListePrepaPojo correspondant pour l'ajouter dans la map
                    IndListePrepaPojo unIndPrepa = new IndListePrepaPojo();
                    // code de la commission from Commission.code
                    unIndPrepa.setCodeCmi(commissionController.getCommission().getCode());
                    // code du dossier de l'individu from IndividuPojo.individu.numDossierOpi
                    unIndPrepa.setNumDossierOpi(iP.getIndividu().getNumDossierOpi());
                    // nom de l'individu from IndividuPojo.individu.nomPatronymique
                    unIndPrepa.setNom(iP.getIndividu().getNomPatronymique());
                    // prenom de l'individu from IndividuPojo.individu.prenom
                    unIndPrepa.setPrenom(iP.getIndividu().getPrenom());
                    // codeEtu de l'individu from IndividuPojo.individu.codeEtu
                    unIndPrepa.setCodeEtu(iP.getIndividu().getCodeEtu());
                    // bac de l'individu from IndividuPojo.individu.indBac
                    // (premier element de la liste)
                    IndBac iB = iP.getIndividu().getIndBac().iterator().next();
                    BacOuxEqu b = getDomainApoService().getBacOuxEqu(
                            iB.getDateObtention(),
                            ExportUtils.isNotNull(iB.getCodBac()));
                    if (b != null) {
                        unIndPrepa.setBac(b.getLibBac());
                    } else {
                        unIndPrepa.setBac(iB.getCodBac());
                    }
                    if (iP.getDerniereAnneeEtudeCursus() != null) {
                        // titre fondant la demande from
                        // IndividuPojo.derniereAnneeEtudeCursus.libCur
                        unIndPrepa.setTitreAccesDemande(
                                iP.getDerniereAnneeEtudeCursus().getLibCur());
                        // dernier cursus from  IndividuPojo.derniereAnneeEtudeCursus.cursus
                        unIndPrepa.setDernierIndCursusScol(iP.getDerniereAnneeEtudeCursus()
                                .getCursus());
                    }
                    // creation d'un indVoeuPojo
                    IndVoeuPojo indPojo = new IndVoeuPojo();
                    indPojo.setIndVoeu(unAvis.getIndVoeu());
                    indPojo.setVrsEtape(vDTO);

                    indPojo.setAvisEnService(unAvis);
                    // on ajoute indPojo
                    unIndPrepa.setIndVoeuxPojo(new HashSet<IndVoeuPojo>());
                    unIndPrepa.getIndVoeuxPojo().add(indPojo);

                    // on ajoute l'indPrepa dans la map
                    mapIndListByEtapeAndAvis.get(vDTO).get(typeDec).add(unIndPrepa);
                }
            }
        }

        // tri de chaque sous liste par ordre alphabetique
        for (Map.Entry<VersionEtapeDTO, Map<TypeDecision, List<IndListePrepaPojo>>>
                indListForOneEtape : mapIndListByEtapeAndAvis.entrySet()) {
            for (Map.Entry<TypeDecision, List<IndListePrepaPojo>>
                    indListForOneAvis : indListForOneEtape.getValue().entrySet()) {
                Collections.sort(indListForOneAvis.getValue(),
                        new ComparatorString(IndListePrepaPojo.class));
            }
        }

        // on recupere le xsl correspondant e l'edition
        // par ordre alphabetique
        String fileNameXsl = Constantes.LISTE_VALIDATION_AVIS_XSL;
        String fileNamePdf = "listeValidationAvis_"
                + commissionController.getCommission().getCode() + ".pdf";
        String fileNameXml = String.valueOf(System.currentTimeMillis())
                + "_" + commissionController.getCommission().getCode() + ".xml";

        // on genere le pdf
        commissionController.generatePDFListePreparatoire(
                fileNameXsl, fileNameXml, fileNamePdf, mapIndListByEtapeAndAvis);
    }

    /**
     * Genere le pdf des notifications.
     *
     * @return String
     */
    public String notificationPdfGeneration() {
        ByteArrayOutputStream zipByteArray = new ByteArrayOutputStream();
        ZipOutputStream zipStream = new ZipOutputStream(zipByteArray);
        // generate the pdf if exist
        if (!this.pdfData.isEmpty()) {
            Set<Commission> lesCommissions = this.pdfData.keySet();
            for (Commission laCommission : lesCommissions) {
                String fileNameXml = String.valueOf(System.currentTimeMillis())
                        + "_" + laCommission.getCode() + ".xml";
                String fileNamePdf = "commission_" + laCommission.getCode() + ".pdf";
                List<NotificationOpinion> lesNotifs = this.pdfData.get(laCommission);

                for (NotificationOpinion n : lesNotifs)
                    if (printOnlyDef) {
                        n.setVoeuxFavorable(new HashSet<IndVoeuPojo>());
                        n.setVoeuxFavorableAppel(new HashSet<IndVoeuPojo>());
                    }
                castorService.objectToFileXml(lesNotifs, fileNameXml);
                CastorService cs = (CastorService) castorService;
                if (lesCommissions.size() > 1) {
                    // zip file
                    PDFUtils.preparePDFinZip(
                            fileNameXml, zipStream,
                            cs.getXslXmlPath(),
                            fileNamePdf, Constantes.NOTIFICATION_IND_XSL);

                } else {
                    // one pdf
                    PDFUtils.exportPDF(fileNameXml, FacesContext.getCurrentInstance(),
                            cs.getXslXmlPath(),
                            fileNamePdf, Constantes.NOTIFICATION_IND_XSL);
                }
            }
            if (lesCommissions.size() > 1) {
                try {
                    zipStream.close();
                } catch (IOException e) {
                    log.error("probleme lors du zipStream.close() "
                            + " les notification des commissions "
                            + "n ont pas ete telechargee");
                }
                PDFUtils.setDownLoadAndSend(
                        zipByteArray.toByteArray(),
                        FacesContext.getCurrentInstance(),
                        Constantes.HTTP_TYPE_ZIP, "NotifsCommissions.zip");
            }
        }
        actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        return NavigationRulesConst.DISPLAY_VALID_OPINIONS;
    }

    /**
     * Make the Map Map < Commission , List < NotificationOpinion>> pdfData.
     *
     * @param individus
     * @param laCommission
     */
    public void makePdfData(final List<IndividuPojo> individus, final Commission laCommission) {
        // hibernate session reattachment
        Commission com = retrieveOSIVCommission(
                laCommission.getId(), laCommission.getCode());

        List<NotificationOpinion> dataPDF = new ArrayList<>();
        for (IndividuPojo i : individus) {
            Set<IndVoeuPojo> indVoeuPojoFav = new HashSet<>();
            Set<IndVoeuPojo> indVoeuPojoDef = new HashSet<>();
            Set<IndVoeuPojo> indVoeuPojoFavAppel = new HashSet<>();
            Set<IndVoeuPojo> indVoeuPojoDefAppel = new HashSet<>();
            for (IndVoeuPojo indVPojo : i.getIndVoeuxPojo()) {
                Avis a = indVPojo.getAvisEnService();
                if (a != null) {
                    if (a.getResult().getIsFinal()
                            && a.getResult().getCodeTypeConvocation()
                            .equals(inscriptionAdm.getCode())) {
                        if (!a.getAppel()) {
                            indVoeuPojoFav.add(indVPojo);
                        } else {
                            indVoeuPojoFavAppel.add(indVPojo);
                        }
                    } else {
                        if (a.getResult().getIsFinal()
                                && a.getResult().getCodeTypeConvocation()
                                .equals(refused.getCode())) {
                            if (!a.getAppel()) {
                                indVoeuPojoDef.add(indVPojo);
                            } else {
                                indVoeuPojoDefAppel.add(indVPojo);
                            }
                        }
                    }
                }
            }
            // data for pdf if necessery
            if (!indVoeuPojoFav.isEmpty() || !indVoeuPojoDef.isEmpty()
                    || !indVoeuPojoFavAppel.isEmpty() || !indVoeuPojoDefAppel.isEmpty()) {
                NotificationOpinion notificationOpinion =
                        initNotificationOpinion(
                                i,
                                com,
                                indVoeuPojoFav,
                                indVoeuPojoDef,
                                indVoeuPojoFavAppel,
                                indVoeuPojoDefAppel);
                dataPDF.add(notificationOpinion);
            }
        }

        // add data to pdfData
        if (!dataPDF.isEmpty()) {
            this.pdfData.put(laCommission, dataPDF);
        }


    }

    /**
     * Initialisation pojo.
     * @return NotificationOpinion
     */
    private NotificationOpinion initNotificationOpinion(
            final IndividuPojo i,
            final Commission laCommission,
            final Set<IndVoeuPojo> indVoeuPojoFav,
            final Set<IndVoeuPojo> indVoeuPojoDef,
            final Set<IndVoeuPojo> indVoeuPojoFavAppel,
            final Set<IndVoeuPojo> indVoeuPojoDefAppel) {

        NotificationOpinion notificationOpinion = new NotificationOpinion();

        notificationOpinion.setVoeuxFavorable(indVoeuPojoFav);
        notificationOpinion.setVoeuxDefavorable(indVoeuPojoDef);
        notificationOpinion.setVoeuxFavorableAppel(indVoeuPojoFavAppel);
        notificationOpinion.setVoeuxDefavorableAppel(indVoeuPojoDefAppel);
        notificationOpinion.setCodeEtu(i.getIndividu().getCodeEtu());
        notificationOpinion.setNom(i.getIndividu().getNomPatronymique());
        notificationOpinion.setNumDossierOpi(i.getIndividu().getNumDossierOpi());
        notificationOpinion.setPrenom(i.getIndividu().getPrenom());
        notificationOpinion.setSexe(i.getIndividu().getSexe());
        notificationOpinion.setPeriodeScolaire(i.getCampagneEnServ(getDomainService()).getCode());

        ContactCommission contactCommission = laCommission.getContactsCommission().get(
                Utilitaires.getCodeRIIndividu(i.getIndividu(), getDomainService()));
        AdressePojo aPojo = null;
        if (contactCommission != null) {
            aPojo = new AdressePojo(contactCommission.getAdresse(), getDomainApoService());
            notificationOpinion.setCoordonneesContact(aPojo);
        }
        if (i.getIndividu().getAdresses() != null && i.getIndividu().getAdresses().get(ADR_FIX) != null)
            aPojo = new AdressePojo(i.getIndividu().getAdresses().get(ADR_FIX), getDomainApoService());

        if (laCommission.getCalendarCmi() != null && laCommission.getCalendarCmi().getEndDatConfRes() != null)
            notificationOpinion.setDateCloture(convertDateToString(laCommission.getCalendarCmi().getEndDatConfRes(), DATE_FORMAT));

        SignataireDTO s = null;
        Integer codeRI = i.getCampagneEnServ(getDomainService()).getCodeRI();

        if (StringUtils.hasText(laCommission.getContactsCommission().get(codeRI).getCodSig()))
            s = getDomainApoService().getSignataire(laCommission.getContactsCommission().get(codeRI).getCodSig());

        notificationOpinion.setSignataire(s);
        notificationOpinion.setNomCommission(laCommission.getContactsCommission().get(codeRI).getCorresponding());
        notificationOpinion.setAdresseEtu(aPojo);

        return notificationOpinion;
    }


    public void generationMailAsyncWarning() {
        addWarnMessage(null, getString("INFO.PRINT.MAIL_ASYNC_WARNING"));
    }

    /**
     * Filter individu by commision, validated avis, typeTraitement not equals transfert;
     * foreach individu filter its voeux by avis enService and typeDecision selected.
     * Better look to delegate to DAO layer directly as it's the same operation performed by
     *  IndividuDaoServiceImpl.typeDecFilter
     */
    public void initIndividus() {
        final List<TypeDecision> typeDecisions = buildSelectedTypeDecision();
        setLesIndividus(new ArrayList<>(
                getIndividus(commissionController.getCommission(), some(false), not(hasTypeTrt(Transfert)))
                        .map(new F<IndividuPojo, IndividuPojo>() {
                            @Override
                            public IndividuPojo f(IndividuPojo individuPojo) {
                                individuPojo.setIndVoeuxPojo(
                                        individuPojo.getIndVoeuxPojo()
                                                .filter(new F<IndVoeuPojo, Boolean>() {
                                                    @Override
                                                    public Boolean f(IndVoeuPojo indVoeuPojo) {
                                                        return !iterableArray(indVoeuPojo.getIndVoeu().getAvis())
                                                                .filter(new F<Avis, Boolean>() {
                                                                    @Override
                                                                    public Boolean f(Avis avis) {
                                                                        return avis.getTemoinEnService() && typeDecisions.contains(avis.getResult());
                                                                    }
                                                                })
                                                                .isEmpty();
                                                    }
                                                }));
                                return individuPojo;
                            }
                        })
                        .toCollection()));
    }

    /**
     * Transform the {@link Object[]} into {@link List}
     * Primefaces hold the datatable's selection into Object[] see this.resultSelected
     * @return List of TypeDecision
     */
    private List<TypeDecision> buildSelectedTypeDecision() {
        List<TypeDecision> result = new ArrayList<>();
        for (Object o : this.resultSelected)
            if (o instanceof TypeDecision) {
                TypeDecision t = (TypeDecision) o;
                result.add(t);
            }
        return result;
    }

    public IndividuController getIndividuController() {
        return individuController;
    }

    public void setIndividuController(IndividuController individuController) {
        this.individuController = individuController;
    }

    public Object[] getCommissionsSelected() {
        return commissionsSelected;
    }

    public void setCommissionsSelected(final Object[] commissionsSelected) {
        this.commissionsSelected = commissionsSelected;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(final Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public List<String> getChampsDispos() {
        return champsDispos;
    }

    public String[] getChampsChoisis() {
        return champsChoisis;
    }

    public void setChampsChoisis(final String[] champsChoisis) {
        this.champsChoisis = champsChoisis;
    }

    public List<IndividuPojo> getLesIndividus() {
        return lesIndividus;
    }

    public void setLesIndividus(final List<IndividuPojo> lesIndividus) {
        this.lesIndividus = lesIndividus;
    }

    public Object[] getResultSelected() {
        return resultSelected;
    }

    public void setResultSelected(final Object[] resultSelected) {
        this.resultSelected = resultSelected;
    }

    public Map<Commission, List<NotificationOpinion>> getPdfData() {
        return pdfData;
    }

    public void setPdfData(final Map<Commission, List<NotificationOpinion>> pdfData) {
        this.pdfData = pdfData;
    }

    public void setCommissionController(final CommissionController commissionController) {
        this.commissionController = commissionController;
    }

    public void setCastorService(final ISerializationService castorService) {
        this.castorService = castorService;
    }

    public InscriptionAdm getInscriptionAdm() {
        return inscriptionAdm;
    }

    public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
        this.inscriptionAdm = inscriptionAdm;
    }

    public Refused getRefused() {
        return refused;
    }

    public void setRefused(final Refused refused) {
        this.refused = refused;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    public Boolean getPrintOnlyDef() {
        return printOnlyDef;
    }

    public void setPrintOnlyDef(final Boolean printOnlyDef) {
        this.printOnlyDef = printOnlyDef;
    }

    public ISerializationService getCastorService() {
        return castorService;
    }

    public void setSmtpService(SmtpService smtpService) {
        this.smtpService = smtpService;
    }

    public IndividuPojo getIndividuPojoSelected() {
        return individuPojoSelected;
    }

    public void setIndividuPojoSelected(final IndividuPojo individuPojoSelected) {
        this.individuPojoSelected = individuPojoSelected;
    }

    public void setExportFormOrbeonController(
            final ExportFormOrbeonController exportFormOrbeonController) {
        this.exportFormOrbeonController = exportFormOrbeonController;
    }

    public IndRechPojo getIndRechPojo() {
        return indRechPojo;
    }

    public LazyDataModel<IndividuPojo> getIndPojoLDM() {
        return indPojoLDM;
    }

    public boolean isRenderTable() {
        return renderTable;
    }

    public void doInitSelectValid() {
        if (indRechPojo.getSelectValid() == null)
            indRechPojo.setSelectValid(false);
    }

    public void doRenderTable() { renderTable = true; }
}

