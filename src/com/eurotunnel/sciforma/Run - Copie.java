///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.eurotunnel.sciforma;
//
//import com.eurotunnel.sciforma.beans.Connector;
//import com.eurotunnel.sciforma.beans.SciformaField;
//import com.eurotunnel.sciforma.manager.IssueManager;
//import com.eurotunnel.sciforma.manager.IssueManagerImpl;
//import com.eurotunnel.sciforma.manager.ProjectManager;
//import com.eurotunnel.sciforma.manager.ProjectManagerImpl;
//import com.sciforma.psnext.api.*;
//import generated.exportecr.ObjectFactory;
//import generated.importecr.*;
//import generated.importecr.ECRs.ECR;
//import generated.importsap.*;
//import generated.importsap.CodeProjets.CodeProjet;
//import generated.importsap.CodeProjets.CodeProjet.Actuals;
//import generated.importsap.CodeProjets.CodeProjet.Actuals.Actual;
//import java.io.File;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//
///**
// *
// * @author Eric
// */
//public class Run {
//
//    public static ApplicationContext ctx;
//
//    private static String IP;
//    private static String PORT;
//    private static String CONTEXTE;
//    private static String USER;
//    private static String PWD;
//
//    public static Session mSession;
//    //private static List<Project> projectList = null;
//    public static Hashtable projects;
//    private static ProjectManager projectManager;
//
//    private static String IMPORT_SAP = "import_from_sap";
//    private static String IMPORT_ECM = "import_from_ecm";
//    private static String EXPORT_ECM = "export_to_ecm";
//
//    private static String CAPEX = "CAPEX";
//    private static String OPEX = "OPEX";
//    private static String LSM = "LSM";
//
//    private static String FILENAME_IMPORT_SAP;
//    private static String VDD_PROJET_BUDGET;
//    private static String VDD_PROJET_BUDGET_TYPE;
//    private static String VDD_PROJET_BUDGET_CODE;
//    private static String VDD_PROJET_BUDGET_REALISE_ACTUAL;
//    private static String VDD_PROJET_BUDGET_ENGAGE_COMMANDE;
//    private static String VDD_PROJET_PRLOG;
//    private static String VDD_PROJET_PRLOG_STAUT_CREATEUR;
//    private static String VDD_PROJET_PRLOG_STAUT_DATE;
//    private static String VDD_PROJET_PRLOG_STATUT_DATEFIN;
//    private static String VDD_PROJET_PRLOG_STATUT_CODE;
//    private static String VDD_PROJET_PRLOG_INIDCATEUR_COMMENT;
//    private static String VDD_PROJET_PRLOG_INIDCATEUR_QUALITE;
//    private static String VDD_PROJET_PRLOG_INIDCATEUR_COUT;
//    private static String VDD_PROJET_PRLOG_INIDCATEUR_DELAI;
//    private static String FILENAME_IMPORT_ECM;
//    private static String FILENAME_EXPORT_ECM;
//    private static String OBJECTIVE_ECR;
//    private static String OBJECTIVE_CATEGORIE;
//    private static String OBJECTIVE_TITRE;
//    private static String OBJECTIVE_DESCRIPTION;
//    private static String OBJECTIVE_TYPE;
//    private static String OBJECTIVE_TYPE_VEHICULE;
//    private static String OBJECTIVE_SYSTEME_PRIMAIRE;
//    private static String OBJECTIVE_TYPE_INVESTISSEMENT;
//    private static String OBJECTIVE_RESULTAT_COTATION;
//    private static String OBJECTIVE_NIVEAU_SOUMISSION;
//    private static String OBJECTIVE_CIG_FLAG;
//    private static String OBJECTIVE_CRITERE;
//    private static String OBJECTIVE_REQUEST;
//    private static String OBJECTIVE_COMMENTAIRE;
//    private static String OBJECTIVE_DEMANDEUR;
//    private static String OBJECTIVE_DATE_DEMANDE;
//    private static String OBJECTIVE_DATE_AFFECTATION;
//    private static String OBJECTIVE_NOM_APPROBATEUR;
//    private static String OBJECTIVE_RAISON_CHANGEMENT;
//    private static String OBJECTIVE_DOMAINE;
//    private static String OBJECTIVE_BUDGET;
//    private static String OBJECTIVE_ANNEE;
//    private static String OBJECTIVE_BUDGET_OBTENU;
//    private static String OBJECTIVE_ACTEUR_ING;
//    private static String OBJECTIVE_ACTEUR_CDP;
//    private static String OBJECTIVE_ACTEUR_QSE;
//    private static String OBJECTIVE_ACTEUR_MAI;
//    private static String VDD_FDL;
//    private static String VDD_FDL_NUMBER;
//    private static String VDD_GED;
//    private static String VDD_GED_BIBLI;
//    private static String VDD_GED_DESC;
//    private static String VDD_GED_DOC;
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args)  {
//        try {
//            initialisation();
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[main] Demarrage de l'API: " + new Date());
//            connection();
//            chargementParametreSciforma();
//            if (args.length >= 1) {
//                if (args[0].equals(IMPORT_SAP)) {
//                    if (args.length == 2) {
//                        doImportSap(args[1]);
//                    } else {
//                        doImportSap("");
//                    }
//                }
//                if (args[0].equals(IMPORT_ECM)) {
//                    doImportEcm();
//                }
//                if (args[0].equals(EXPORT_ECM)) {
//                    doExportEcm();
//                }
//            }
//            mSession.logout();
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[main] Fermeture de l'API: " + new Date());
//            
//        } catch (PSException ex) {
//            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        System.exit(0);
//    }
//
//    private static void initialisation() {
//        ctx = new FileSystemXmlApplicationContext(System.getProperty("user.dir") + System.getProperty("file.separator") + "config" + System.getProperty("file.separator") + "applicationContext.xml");
//    }
//
//    private static void connection() {
//        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[connexion] Chargement parametre connexion:" + new Date());
//        Connector c = (Connector) ctx.getBean("sciforma");
//        USER = c.getUSER();
//        PWD = c.getPWD();
//        IP = c.getIP();
//        PORT = c.getPORT();
//        CONTEXTE = c.getCONTEXTE();
//
//        try {
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[connexion] Initialisation de la Session:" + new Date());
//            String url = IP + "/" + CONTEXTE;
//            mSession = new Session(url);
//            mSession.login(USER, PWD.toCharArray());
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[connexion] Connecté: " + new Date() + " Ã  l'instance " + CONTEXTE);
//        } catch (PSException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[connexion] Erreur dans la connection de ... " + CONTEXTE, ex);
//        }
//    }
//
//    private static void doImportSap(String idProjet) throws PSException {
//        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap] Importation des données XML de SAP: " + new Date());
//        try {
//            File file = new File(FILENAME_IMPORT_SAP);
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap] Lecture du fichier: " + FILENAME_IMPORT_SAP);
//            JAXBContext jaxbContext = JAXBContext.newInstance(CodeProjets.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap] Unmarshalling du fichier: " + FILENAME_IMPORT_SAP);
//            CodeProjets cps = (CodeProjets) jaxbUnmarshaller.unmarshal(file);
//            loadCodeProject(idProjet);
//            int compteur = 1;
//            List<CodeProjet> codeList = cps.getCodeProjet();
//            for (CodeProjet cp : codeList) {
//                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap] Code projet trouvé: " + cp.getCode() + " [Code " + compteur + "/" + codeList.size() + "]");
//                String id_projet;
//                id_projet = findIdProjet(cp.getCode());
//                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap] Recherche de l'id projet: " + id_projet);
//                Project p = null;
//                if (id_projet != null) {
//                    p = projectManager.findProjectById(id_projet);
//                }
//                if (p != null) {
//                    if (!p.isLocked()) {
//                        p.open(false);
//                        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap] Projet trouvé: " + p.getStringField("Name"));
//                        List pl = mSession.getDataViewRowList(VDD_PROJET_BUDGET, p);
//                        Iterator pit = pl.iterator();
//                        while (pit.hasNext()) {
//                            DataViewRow dvr = (DataViewRow) pit.next();
//                            if (dvr.getStringField(VDD_PROJET_BUDGET_CODE).equals(cp.getCode())) {
//                                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap][UPDATE] enregistrement  dans la vdd pour le code: " + cp.getCode());
//                                updateDVR(cp, dvr);
//                                //printDataViewRow(dvr);
//                            }
//                        }
////                        if (!find) {
////                            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[INSERT] aucun enregistrement dans la vdd pour le code: " + current_code_budgetaire);
////                            insertDVR(cp, p);
////                        }
//                        p.save();
//                        p.close();
//                        projects.remove(cp.getCode());
//                    } else {
//                        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap]Projet verrouillé: [" + id_projet +"] "+ p.getStringField("Name"));
//                    }
//                } else {
//                    Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportSap] Aucun projet trouvé pour l'id: " + id_projet);
//                }
//                compteur++;
//            }
//        } catch (PSException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportSap] Erreur dans classe Sciforma" + new Date(), ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportSap] Erreur dans le parsing XML" + new Date(), ex);
//            System.exit(1);
//        }catch (Exception ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportSap] Erreur java" + new Date(), ex);
//            System.exit(1);
//        }
//        logCodeProjectNotUse();
//    }
//
//    private static void doImportEcm() {
//        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportEcm]Importation des donnÃ©es XML de ECM: " + new Date());
//        try {
//            //Lecture xml de SAP
//            File file = new File(FILENAME_IMPORT_ECM);
//            List userList = mSession.getUserList();
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportEcm] Lecture du fichier: " + FILENAME_IMPORT_ECM);
//            JAXBContext jaxbContext = JAXBContext.newInstance(ECRs.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            ECRs ecrs = (ECRs) jaxbUnmarshaller.unmarshal(file);
//            List<ECR> lecrs = ecrs.getECR();
//            for (ECR ecr : lecrs) {
//                String projName = "";
//                String projID = String.valueOf(Math.random());
//                if(ecr.getTitre() != null)
//                    projName = ecr.getTitre();
//                if(ecr.getCodeLabel() != null)
//                    projID = ecr.getCodeLabel();
//                Project newProject = new Project(projName, projID, Project.VERSION_OBJECTIVE);
//                newProject.setStringField("ID", projID);
//                if (!OBJECTIVE_ECR.isEmpty() && ecr.getCodeLabel() != null) {
//                    newProject.setStringField(OBJECTIVE_ECR, ecr.getCodeLabel());
//                }
//                if (!OBJECTIVE_CATEGORIE.isEmpty() && ecr.getCategorie() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_CATEGORIE, ecr.getCategorie());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_DESCRIPTION.isEmpty() && ecr.getDescription() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_DESCRIPTION, ecr.getDescription());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_TYPE.isEmpty() && ecr.getType() != null ) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_TYPE, ecr.getType());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_TYPE_VEHICULE.isEmpty() && ecr.getTypesDeVehicule().getTypeDeVehicule() != null ) {
//                    try {
//                        newProject.setListField(OBJECTIVE_TYPE_VEHICULE, ecr.getTypesDeVehicule().getTypeDeVehicule());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_SYSTEME_PRIMAIRE.isEmpty() && ecr.getSystemesPrimaires().getSystemePrimaire() != null) {
//                    try {
//                        newProject.setListField(OBJECTIVE_SYSTEME_PRIMAIRE, ecr.getSystemesPrimaires().getSystemePrimaire());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!VDD_FDL_NUMBER.isEmpty() && ecr.getFicheDeLancement() != 0) {
//                    try {
//                        DataViewRow dv = new DataViewRow(VDD_FDL, newProject, DataViewRow.CREATE);
//                        dv.setStringField(VDD_FDL_NUMBER, String.valueOf(ecr.getFicheDeLancement()));
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_TYPE_INVESTISSEMENT.isEmpty() && ecr.getTypeInvestissement() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_TYPE_INVESTISSEMENT, ecr.getTypeInvestissement());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_RESULTAT_COTATION.isEmpty() && ecr.getResultatCotation() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_RESULTAT_COTATION, ecr.getResultatCotation());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_NIVEAU_SOUMISSION.isEmpty() && ecr.getNiveauSoumissionCIG() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_NIVEAU_SOUMISSION, ecr.getNiveauSoumissionCIG());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_CIG_FLAG.isEmpty() && ecr.getCIGFlag() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_CIG_FLAG, ecr.getCIGFlag());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_CRITERE.isEmpty() && ecr.getCIGCriteres() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_CRITERE, ecr.getCIGCriteres());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_REQUEST.isEmpty() && ecr.getCIGRequest()!= null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_REQUEST, ecr.getCIGRequest());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_COMMENTAIRE.isEmpty() && ecr.getCommentairesAdditionnels() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_COMMENTAIRE, ecr.getCommentairesAdditionnels());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_DATE_AFFECTATION.isEmpty() && ecr.getDateAffectation() != null) {
//                    try {
//                        SimpleDateFormat format = new SimpleDateFormat();
//                        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                        Date strToDate = format.parse(ecr.getDateAffectation());
//                        newProject.setDateField(OBJECTIVE_DATE_AFFECTATION, strToDate);
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                
//                if (!OBJECTIVE_DATE_DEMANDE.isEmpty() && ecr.getDateDemande() != null) {
//                    try {
//                        SimpleDateFormat format = new SimpleDateFormat();
//                        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                        Date strToDate = format.parse(ecr.getDateDemande());
//                        newProject.setDateField(OBJECTIVE_DATE_DEMANDE, strToDate);
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                
//                if (!OBJECTIVE_DEMANDEUR.isEmpty() && ecr.getNomDemandeur() != null) {
//                    try {
//                        Iterator usrIt = userList.iterator();
//                        String name = "";
//                        while (usrIt.hasNext()) {
//                            User usr = (User) (usrIt.next());
//                            if (usr.getStringField("Login ID").equals(ecr.getNomDemandeur().toLowerCase())) {
//                                name = usr.getStringField("Name");
//                            }
//                        }
//                        newProject.setStringField(OBJECTIVE_DEMANDEUR, name); //utilisateur
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                    //utilisateur newProject.setStringField("Manager 1", "Administrator");
//                }//                if(!OBJECTIVE_DATE_DEMANDE.isEmpty())
//                if (!OBJECTIVE_NOM_APPROBATEUR.isEmpty() && ecr.getNomApprobateur() != null) {
//                    try {
//                        Iterator usrIt = userList.iterator();
//                        String name = "";
//                        while (usrIt.hasNext()) {
//                            User usr = (User) (usrIt.next());
//                            if (usr.getStringField("Login ID").equals(ecr.getNomApprobateur().toLowerCase())) {
//                                name = usr.getStringField("Name");
//                            }
//                        }
//                        ArrayList l = new ArrayList();
//                        l.add(name);
//                        newProject.setListField(OBJECTIVE_NOM_APPROBATEUR, l); //utilisateur
//                    } catch (Exception e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//
//                if (!OBJECTIVE_RAISON_CHANGEMENT.isEmpty() && ecr.getRaisonChangement() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_RAISON_CHANGEMENT, ecr.getRaisonChangement());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_DOMAINE.isEmpty() && ecr.getDomaine() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_DOMAINE, ecr.getDomaine());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_BUDGET.isEmpty() && ecr.getBudget() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_BUDGET, ecr.getBudget());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_ANNEE.isEmpty() && ecr.getAnnees().getAnnee() != null) {
//                    try {
//                        newProject.setListField(OBJECTIVE_ANNEE, ecr.getAnnees().getAnnee());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_BUDGET_OBTENU.isEmpty() && ecr.getBudgetObtenu() != null) {
//                    try {
//                        newProject.setStringField(OBJECTIVE_BUDGET_OBTENU, ecr.getBudgetObtenu());
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!VDD_GED_DOC.isEmpty() && ecr.getDocuments().getDocument() != null) {
//                    try {
//                        for(Iterator<ECR.Documents.Document> it = ecr.getDocuments().getDocument().iterator(); it.hasNext();){
//                            ECR.Documents.Document document = it.next();
//                            DataViewRow dv = new DataViewRow(VDD_GED, newProject, DataViewRow.CREATE);
//                            dv.setStringField(VDD_GED_BIBLI, document.getLibrairie());
//                            dv.setStringField(VDD_GED_DESC, document.getTitre());
//                            dv.setStringField(VDD_GED_DOC, document.getDocid().toString());
//                        }
//                    } catch (InvalidDataException e) {
//                        Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                    }
//                }
//                if (!OBJECTIVE_ACTEUR_CDP.isEmpty() && ecr.getActeurs().getActeur() != null) {
//                    for (Iterator<ECR.Acteurs.Acteur> it = ecr.getActeurs().getActeur().iterator(); it.hasNext();) {
//                        ECR.Acteurs.Acteur acteur = it.next();
//                        if (acteur.getTitre().equals("Chef de projet")) {
//                            try {
//                                Iterator usrIt = userList.iterator();
//                                String name = "";
//                                while (usrIt.hasNext()) {
//                                    User usr = (User) (usrIt.next());
//                                    if (usr.getStringField("Login ID").equals(acteur.getLogin().toLowerCase())) {
//                                        name = usr.getStringField("Name");
//                                    }
//                                }
//                                newProject.setStringField(OBJECTIVE_ACTEUR_CDP, name); //utilisateur
//                            } catch (Exception e) {
//                                Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                            }
//                        }
//                    }
//                }
//                if (!OBJECTIVE_ACTEUR_ING.isEmpty() && ecr.getActeurs().getActeur() != null) {
//                    for (Iterator<ECR.Acteurs.Acteur> it = ecr.getActeurs().getActeur().iterator(); it.hasNext();) {
//                        ECR.Acteurs.Acteur acteur = it.next();
//                        if (acteur.getTitre().equals("Ingenerie")) {
//                            try {
//                                Iterator usrIt = userList.iterator();
//                                String name = "";
//                                while (usrIt.hasNext()) {
//                                    User usr = (User) (usrIt.next());
//                                    if (usr.getStringField("Login ID").equals(acteur.getLogin().toLowerCase())) {
//                                        name = usr.getStringField("Name");
//                                    }
//                                }
//                                ArrayList l = new ArrayList();
//                                l.add(name);
//                                newProject.setListField(OBJECTIVE_ACTEUR_ING, l); //utilisateur
//                            } catch (Exception e) {
//                                Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                            }
//                        }
//                    }
//                }
//                if (!OBJECTIVE_ACTEUR_MAI.isEmpty() && ecr.getActeurs().getActeur() != null) {
//                    for (Iterator<ECR.Acteurs.Acteur> it = ecr.getActeurs().getActeur().iterator(); it.hasNext();) {
//                        ECR.Acteurs.Acteur acteur = it.next();
//                        if (acteur.getTitre().equals("Mainetenance")) {
//                            try {
//                                Iterator usrIt = userList.iterator();
//                                String name = "";
//                                while (usrIt.hasNext()) {
//                                    User usr = (User) (usrIt.next());
//                                    if (usr.getStringField("Login ID").equals(acteur.getLogin().toLowerCase())) {
//                                        name = usr.getStringField("Name");
//                                    }
//                                }
//                                ArrayList l = new ArrayList();
//                                l.add(name);
//                                newProject.setListField(OBJECTIVE_ACTEUR_MAI, l); //utilisateur
//                            } catch (Exception e) {
//                                Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                            }
//                        }
//                    }
//                }
//                if (!OBJECTIVE_ACTEUR_QSE.isEmpty() && ecr.getActeurs().getActeur() != null) {
//                    for (Iterator<ECR.Acteurs.Acteur> it = ecr.getActeurs().getActeur().iterator(); it.hasNext();) {
//                        ECR.Acteurs.Acteur acteur = it.next();
//                        if (acteur.getTitre().equals("Approbateur")) {
//                            try {
//                                Iterator usrIt = userList.iterator();
//                                String name = "";
//                                while (usrIt.hasNext()) {
//                                    User usr = (User) (usrIt.next());
//                                    if (usr.getStringField("Login ID").equals(acteur.getLogin().toLowerCase())) {
//                                        name = usr.getStringField("Name");
//                                    }
//                                }
//                                ArrayList l = new ArrayList();
//                                l.add(name);
//                                newProject.setListField(OBJECTIVE_ACTEUR_QSE, l); //utilisateur
//                            } catch (Exception e) {
//                                Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] ERREUR Données XML", e);
//                            }
//                        }
//                    }
//                }
//
//                newProject.saveAs(projName, Project.VERSION_OBJECTIVE);
//                newProject.close();
//                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doImportEcm]Projet enregistré : " + new Date());
//            }
//        } catch (PSException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] Erreur dans classe Sciforma" + new Date(), ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] Erreur dans le parsing XML" + new Date(), ex);
//            System.exit(1);
//        }catch (Exception ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doImportEcm] Erreur java" + new Date(), ex);
//            System.exit(1);
//        }
//    }
//
//    private static void doExportEcm() {
//        try {
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doExportEcm]Exportation des donnéees XML pour ECM: " + new Date());
//            ObjectFactory objFactory = new ObjectFactory();
//            generated.exportecr.ECRs ecrs = (generated.exportecr.ECRs) objFactory.createECRs();
//            List ecr = ecrs.getECR();
//            List<Project> lp = mSession.getProjectList(Project.VERSION_WORKING, Project.READWRITE_ACCESS);
//            Iterator lpit = lp.iterator();
//            while (lpit.hasNext()) {
//                Project p = (Project) lpit.next();
//                p.open(true);
//                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doExportEcm]Traitement du projet : " + p.getStringField("Name"));
//                if (!p.getStringField(OBJECTIVE_ECR).isEmpty()) {
//                    generated.exportecr.ECRs.ECR e = objFactory.createECRsECR();
//                    e.setCodeLabel(p.getStringField(OBJECTIVE_ECR));
//                    generated.exportecr.ECRs.ECR.Statuts statuts = objFactory.createECRsECRStatuts();
//                    List s = statuts.getStatut();
//                    List pl = mSession.getDataViewRowList(VDD_PROJET_PRLOG, p);
//                    Iterator pit = pl.iterator();
//                    while (pit.hasNext()) {
//                        DataViewRow dvr = (DataViewRow) pit.next();
//                        if (!pit.hasNext()) {
//                            generated.exportecr.ECRs.ECR.Statuts.Statut statut = objFactory.createECRsECRStatutsStatut();
//                            statut.setIndicateurComment(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_COMMENT));
//                            statut.setIndicateurCout(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_COUT));
//                            statut.setIndicateurDelai(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_DELAI));
//                            statut.setIndicateurQualite(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_QUALITE));
//                            statut.setStatutCode(dvr.getStringField(VDD_PROJET_PRLOG_STATUT_CODE));
//                            statut.setStatutCreateur(dvr.getStringField(VDD_PROJET_PRLOG_STAUT_CREATEUR));
//                            statut.setStatutDate(dvr.getDateField(VDD_PROJET_PRLOG_STAUT_DATE).toString());
//                            statut.setStatutDatefin(dvr.getDateField(VDD_PROJET_PRLOG_STATUT_DATEFIN).toString());
//                            s.add(statut);
//                        }
//                    }
//                    e.setStatuts(statuts);
//                    ecr.add(e);
//                    p.close();
//                }
//            }
//
//            lp = mSession.getProjectList(Project.VERSION_OBJECTIVE, Project.READWRITE_ACCESS);
//            lpit = lp.iterator();
//            while (lpit.hasNext()) {
//                Project p = (Project) lpit.next();
//                p.open(true);
//                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doExportEcm]Traitement du projet : " + p.getStringField("Name"));
//                if (!p.getStringField(OBJECTIVE_ECR).isEmpty()) {
//                    generated.exportecr.ECRs.ECR e = objFactory.createECRsECR();
//                    e.setCodeLabel(p.getStringField(OBJECTIVE_ECR));
//                    generated.exportecr.ECRs.ECR.Statuts statuts = objFactory.createECRsECRStatuts();
//                    List s = statuts.getStatut();
//                    List pl = mSession.getDataViewRowList(VDD_PROJET_PRLOG, p);
//                    Iterator pit = pl.iterator();
//                    while (pit.hasNext()) {
//                        DataViewRow dvr = (DataViewRow) pit.next();
//                        if (!pit.hasNext()) {
//                            generated.exportecr.ECRs.ECR.Statuts.Statut statut = objFactory.createECRsECRStatutsStatut();
//                            statut.setIndicateurComment(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_COMMENT));
//                            statut.setIndicateurCout(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_COUT));
//                            statut.setIndicateurDelai(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_DELAI));
//                            statut.setIndicateurQualite(dvr.getStringField(VDD_PROJET_PRLOG_INIDCATEUR_QUALITE));
//                            statut.setStatutCode(dvr.getStringField(VDD_PROJET_PRLOG_STATUT_CODE));
//                            statut.setStatutCreateur(dvr.getStringField(VDD_PROJET_PRLOG_STAUT_CREATEUR));
//                            statut.setStatutDate(dvr.getDateField(VDD_PROJET_PRLOG_STAUT_DATE).toString());
//                            statut.setStatutDatefin(dvr.getDateField(VDD_PROJET_PRLOG_STATUT_DATEFIN).toString());
//                            s.add(statut);
//                        }
//                    }
//                    e.setStatuts(statuts);
//                    ecr.add(e);
//                    p.close();
//                }
//            }
//
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doExportEcm]Ecriture du fichier: " + new Date());
//            JAXBContext jaxbContext = JAXBContext.newInstance(generated.exportecr.ECRs.class);
//            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
//            File file = new File(FILENAME_EXPORT_ECM);
//            marshaller.marshal(ecrs, file);
//        } catch (InvalidDataException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doExportEcm] Erreur dans les donnees" + new Date(), ex);
//        } catch (PSException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doExportEcm] Erreur dans classe Sciforma" + new Date(), ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doExportEcm] Erreur dans le parsing XML" + new Date(), ex);
//            System.exit(1);
//        }catch (Exception ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[doExportEcm] Erreur java " + new Date(), ex);
//            System.exit(1);
//        }
//        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[doExportEcm] Fin Exportation des données XML pour ECM: " + new Date());
//    }
//
//    private static void chargementParametreSciforma() throws PSException {
//        try {
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[chargementParametreSciforma] Demarrage du chargement des parametres de l'application:" + new Date());
//            projectManager = new ProjectManagerImpl(mSession);
//            //IssueManager = new IssueManagerImpl(mSession);
//            FILENAME_IMPORT_SAP = ((SciformaField) ctx.getBean("sap_to_sciforma")).getSciformaField();
//            FILENAME_IMPORT_ECM = ((SciformaField) ctx.getBean("ecm_to_sciforma")).getSciformaField();
//            FILENAME_EXPORT_ECM = ((SciformaField) ctx.getBean("sciforma_to_ecm")).getSciformaField();
//            VDD_PROJET_BUDGET = ((SciformaField) ctx.getBean("vdd_projet_budget")).getSciformaField();
//            VDD_PROJET_BUDGET_TYPE = ((SciformaField) ctx.getBean("vdd_projet_budget_type")).getSciformaField();
//            VDD_PROJET_BUDGET_CODE = ((SciformaField) ctx.getBean("vdd_projet_budget_code")).getSciformaField();
//            VDD_PROJET_BUDGET_REALISE_ACTUAL = ((SciformaField) ctx.getBean("vdd_projet_budget_realise_actual")).getSciformaField();
//            VDD_PROJET_BUDGET_ENGAGE_COMMANDE = ((SciformaField) ctx.getBean("vdd_projet_budget_engage_commande")).getSciformaField();
//            OBJECTIVE_ECR = ((SciformaField) ctx.getBean("objective_ecr")).getSciformaField();
//            OBJECTIVE_CATEGORIE = ((SciformaField) ctx.getBean("objective_categorie")).getSciformaField();
//            OBJECTIVE_TITRE = ((SciformaField) ctx.getBean("objective_titre")).getSciformaField();
//            OBJECTIVE_DESCRIPTION = ((SciformaField) ctx.getBean("objective_description")).getSciformaField();
//            OBJECTIVE_TYPE = ((SciformaField) ctx.getBean("objective_type")).getSciformaField();
//            OBJECTIVE_TYPE_VEHICULE = ((SciformaField) ctx.getBean("objective_type_de_vehicule")).getSciformaField();
//            OBJECTIVE_SYSTEME_PRIMAIRE = ((SciformaField) ctx.getBean("objective_systeme_primaire")).getSciformaField();
//            OBJECTIVE_TYPE_INVESTISSEMENT = ((SciformaField) ctx.getBean("objective_type_investissement")).getSciformaField();
//            OBJECTIVE_RESULTAT_COTATION = ((SciformaField) ctx.getBean("objective_resultat_cotation")).getSciformaField();
//            OBJECTIVE_NIVEAU_SOUMISSION = ((SciformaField) ctx.getBean("objective_niveau_soumission_CIG")).getSciformaField();
//            OBJECTIVE_CIG_FLAG = ((SciformaField) ctx.getBean("objective_cig_flag")).getSciformaField();
//            OBJECTIVE_CRITERE = ((SciformaField) ctx.getBean("objective_criteres_CIG")).getSciformaField();
//            OBJECTIVE_REQUEST = ((SciformaField) ctx.getBean("objective_request_CIG")).getSciformaField();
//            OBJECTIVE_COMMENTAIRE = ((SciformaField) ctx.getBean("objective_commentaires_additionnels")).getSciformaField();
//            OBJECTIVE_DEMANDEUR = ((SciformaField) ctx.getBean("objective_nom_demandeur")).getSciformaField();
//            OBJECTIVE_DATE_DEMANDE = ((SciformaField) ctx.getBean("objective_date_demande")).getSciformaField();
//            OBJECTIVE_DATE_AFFECTATION = ((SciformaField) ctx.getBean("objective_date_affectation")).getSciformaField();
//            OBJECTIVE_NOM_APPROBATEUR = ((SciformaField) ctx.getBean("objective_nomApprobateur")).getSciformaField();
//            OBJECTIVE_RAISON_CHANGEMENT = ((SciformaField) ctx.getBean("objective_raison_changement")).getSciformaField();
//            OBJECTIVE_DOMAINE = ((SciformaField) ctx.getBean("objective_domaine")).getSciformaField();
//            OBJECTIVE_BUDGET = ((SciformaField) ctx.getBean("objective_budget")).getSciformaField();
//            OBJECTIVE_ANNEE = ((SciformaField) ctx.getBean("objective_annee")).getSciformaField();
//            OBJECTIVE_BUDGET_OBTENU = ((SciformaField) ctx.getBean("objective_budget_obtenu")).getSciformaField();
//            OBJECTIVE_ACTEUR_CDP = ((SciformaField) ctx.getBean("objective_acteur_cdp")).getSciformaField();
//            OBJECTIVE_ACTEUR_ING = ((SciformaField) ctx.getBean("objective_acteur_ing")).getSciformaField();
//            OBJECTIVE_ACTEUR_MAI = ((SciformaField) ctx.getBean("objective_acteur_mai")).getSciformaField();
//            OBJECTIVE_ACTEUR_QSE = ((SciformaField) ctx.getBean("objective_acteur_qse")).getSciformaField();
//            VDD_PROJET_PRLOG = ((SciformaField) ctx.getBean("prlog")).getSciformaField();
//            VDD_PROJET_PRLOG_STAUT_CREATEUR = ((SciformaField) ctx.getBean("prlog_statut_createur")).getSciformaField();
//            VDD_PROJET_PRLOG_STAUT_DATE = ((SciformaField) ctx.getBean("prlog_statut_date")).getSciformaField();
//            VDD_PROJET_PRLOG_STATUT_DATEFIN = ((SciformaField) ctx.getBean("prlog_statut_datefin")).getSciformaField();
//            VDD_PROJET_PRLOG_STATUT_CODE = ((SciformaField) ctx.getBean("prlog_statut_code")).getSciformaField();
//            VDD_PROJET_PRLOG_INIDCATEUR_COMMENT = ((SciformaField) ctx.getBean("prlog_indicateur_comment")).getSciformaField();
//            VDD_PROJET_PRLOG_INIDCATEUR_QUALITE = ((SciformaField) ctx.getBean("prlog_indicateur_qualite")).getSciformaField();
//            VDD_PROJET_PRLOG_INIDCATEUR_COUT = ((SciformaField) ctx.getBean("prlog_indicateur_cout")).getSciformaField();
//            VDD_PROJET_PRLOG_INIDCATEUR_DELAI = ((SciformaField) ctx.getBean("prlog_indicateur_delai")).getSciformaField();
//            VDD_FDL  = ((SciformaField) ctx.getBean("et_fdl")).getSciformaField();
//            VDD_FDL_NUMBER  = ((SciformaField) ctx.getBean("et_fdl_number")).getSciformaField();
//            VDD_GED = ((SciformaField) ctx.getBean("et_ged")).getSciformaField();
//            VDD_GED_BIBLI  = ((SciformaField) ctx.getBean("et_ged_bibli")).getSciformaField();
//            VDD_GED_DESC  = ((SciformaField) ctx.getBean("et_ged_descr")).getSciformaField();
//            VDD_GED_DOC  = ((SciformaField) ctx.getBean("et_ged_doc")).getSciformaField();
//
//            //OBJECTIVE_ACTEUR = ((SciformaField) ctx.getBean("objective_acteur")).getSciformaField();
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[chargementParametreSciforma] Fin du chargement des parametres de l'application:" + new Date());
//        } catch (Exception ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[chargementParametreSciforma] Erreur dans la lecture l'intitialisation du parametrage " + new Date(), ex);
//            mSession.logout();
//            System.exit(1);
//        }
//    }
//
//    private static void loadCodeProject(String id_projet) throws PSException {
//        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[loadCodeProject] Mise en mémoire code project");
//        Boolean open = false;
//        projects = new Hashtable();
//        try {
//            List<Project> lp = mSession.getProjectList(Project.VERSION_WORKING, Project.READWRITE_ACCESS);
//            Iterator lpit = lp.iterator();
//            while (lpit.hasNext()) {
//                Project p = (Project) lpit.next();
//                if (!id_projet.isEmpty()) {
//                    if (p.getID().equals(id_projet)) {
//                        p.open(true);
//                        open = true;
//                    }
//                } else {
//                    p.open(true);
//                    open = true;
//                }
//                if (open) {
//                    List pl = mSession.getDataViewRowList(VDD_PROJET_BUDGET, p);
//                    Iterator pit = pl.iterator();
//                    while (pit.hasNext()) {
//                        DataViewRow dvr = (DataViewRow) pit.next();
//                        String code_budget = null;
//                        if (dvr.getStringField(VDD_PROJET_BUDGET_CODE).length() > 1) {
//                            //Demande de retirer le contrôle sur le type, on met les if en commentaire
//                           // if (dvr.getStringField(VDD_PROJET_BUDGET_TYPE).equals(CAPEX) && dvr.getStringField(VDD_PROJET_BUDGET_CODE).startsWith("Z")) {
//                                //code_budget = dvr.getStringField(VDD_PROJET_BUDGET_CODE).substring(0, 5) + "I";
//                                code_budget = dvr.getStringField(VDD_PROJET_BUDGET_CODE);
//                            //}
//                            /*if (dvr.getStringField(VDD_PROJET_BUDGET_TYPE).equals(OPEX) && dvr.getStringField(VDD_PROJET_BUDGET_CODE).startsWith("Z")) {
//                                //code_budget = dvr.getStringField(VDD_PROJET_BUDGET_CODE).substring(0, 5);
//                                code_budget = dvr.getStringField(VDD_PROJET_BUDGET_CODE);
//                            }
//                            if (dvr.getStringField(VDD_PROJET_BUDGET_TYPE).equals(LSM) && dvr.getStringField(VDD_PROJET_BUDGET_CODE).startsWith("R")) {
//                                //code_budget = dvr.getStringField(VDD_PROJET_BUDGET_CODE).substring(0, 15);
//                                code_budget = dvr.getStringField(VDD_PROJET_BUDGET_CODE);
//                            }*/
//                            if (code_budget != null) {
//                                projects.put(code_budget, p.getID());
//                            } else {
//                                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[loadCodeProject] Discordance de données code vdd: " + dvr.getStringField(VDD_PROJET_BUDGET_CODE) + " pour le type " + dvr.getStringField(VDD_PROJET_BUDGET_TYPE));
//                            }
//                            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[loadCodeProject] Mise en mémoire du code " + code_budget + " pour le projet " + p.getStringField("Name"));
//                        }
//                    }
//                    p.close();
//                }
//                open = false;
//            }
//
//        } catch (InvalidDataException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[loadCodeProject] Erreur dans la lecture des codes budgétaire" + new Date(), ex);
//            mSession.logout();
//            System.exit(1);
//        } catch (PSException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[loadCodeProject] Erreur dans la lecture des codes budgétaire" + new Date(), ex);
//            mSession.logout();
//            System.exit(1);
//        } catch (Exception ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[loadCodeProject] Erreur dans la lecture des codes budgétaire" + new Date(), ex);
//            mSession.logout();
//            System.exit(1);
//        }
//
//    }
//
//    private static String findIdProjet(String code) {
//        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[findIdProjet] Recherche d'un id project pour le code: " + code);
//        if (projects.get(code) != null) {
//            return projects.get(code).toString();
//        } else {
//            return null;
//        }
//    }
//
//    private static void updateDVR(CodeProjet cp, DataViewRow dvr) throws PSException {
//        try {
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[updateDVR] Début mise a jour vue de donnee pour le code: " + cp.getCode());
//            List<DoubleDatedData> lddd = dvr.getDatedData(VDD_PROJET_BUDGET_REALISE_ACTUAL, DatedData.MONTH);
//            List<Actuals> actualsList = cp.getActuals();
//            //Parcourt de tous les Actuals (annÃ©es)
//            for (Actuals actuals : actualsList) {
//                String year = "20" + actuals.getExercice().substring(4);
//                //Parcourt de tous les Actual (mois)
//                List<Actual> actualList = actuals.getActual();
//                for (Actual actual : actualList) {
//                    String month = actual.getMois().toString();
//                    Float montant = actual.getMontantActual();
//                    Calendar c1 = Calendar.getInstance();
//                    c1.set(Integer.parseInt(year), (Integer.parseInt(month) - 1), 1, 0, 0, 0);
//                    Calendar c2 = Calendar.getInstance();
//                    c2.set(Calendar.MONTH, (Integer.parseInt(month) - 1));
//                    c2.set(Integer.parseInt(year), (Integer.parseInt(month) - 1), c2.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
//                    DoubleDatedData ddd = new DoubleDatedData(montant, c1.getTime(), c2.getTime());
//                    Logger.getLogger(Run.class.getName()).log(Level.INFO, "[updateDVR] Mise a jour DoubleDatedData: " + montant + " de: " + c1.getTime() + " a: " + c2.getTime());
//                    lddd.add(ddd);
//                }
//            }
//            dvr.updateDatedData(VDD_PROJET_BUDGET_REALISE_ACTUAL, lddd);
//            dvr.setDoubleField(VDD_PROJET_BUDGET_ENGAGE_COMMANDE, cp.getMontantEngage());
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[updateDVR] Mise a jour VDD_PROJET_BUDGET_ENGAGE_COMMANDE: " + cp.getMontantEngage());
//            Logger.getLogger(Run.class.getName()).log(Level.INFO, "[updateDVR] Fin mise a jour vue de donnee pour le code: " + cp.getCode());
//        } catch (PSException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[updateDVR] Erreur Sciforma " + new Date(), ex);
//        }catch (Exception ex) {
//                Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[updateDVR] Erreur java " + new Date(), ex);
//                mSession.logout();
//                System.exit(1);
//        }
//    }
//
//    private static void insertDVR(CodeProjet cp, Project proj) {
//        try {
//            String current_type_budgetaire = null;//A definir si jamais
//            DataViewRow dv = new DataViewRow(VDD_PROJET_BUDGET, proj, DataViewRow.CREATE);
//            dv.setStringField(VDD_PROJET_BUDGET, current_type_budgetaire);
//            updateDVR(cp, dv);
//        } catch (PSException ex) {
//            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//    }
//
//    private static void logCodeProjectNotUse() throws PSException {
//        Logger.getLogger(Run.class.getName()).log(Level.INFO, "[logCodeProjectNotUse] ==============================  ");
//        Set<String> set = projects.keySet();
//        Iterator<String> it = set.iterator();
//        while (it.hasNext()) {
//            String id = it.next();
//            try {
//                Logger.getLogger(Run.class.getName()).log(Level.INFO, "[logCodeProjectNotUse] Le code " + id + " qui se trouve dans le projet [" + projects.get(id).toString() + "] " + projectManager.findProjectById(projects.get(id).toString()).getStringField("Name") + " n'est pas utilisé.");
//            } catch (PSException ex) {
//                Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[logCodeProjectNotUse] Erreur Sciforma " + new Date(), ex);
//            }catch (Exception ex) {
//                Logger.getLogger(Run.class.getName()).log(Level.ERROR, "[logCodeProjectNotUse] Erreur java " + new Date(), ex);
//                mSession.logout();
//                System.exit(1);
//        }
//        }
//    }
//
//}
