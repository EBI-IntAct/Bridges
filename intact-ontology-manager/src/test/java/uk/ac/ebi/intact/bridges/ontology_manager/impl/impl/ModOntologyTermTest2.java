package uk.ac.ebi.intact.bridges.ontology_manager.impl.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.tools.ontology_manager.client.OlsClient;
import uk.ac.ebi.intact.bridges.ontology_manager.TermDbXref;
import uk.ac.ebi.intact.bridges.ontology_manager.impl.ModOntologyTerm;

import java.rmi.RemoteException;

/**
 * Second tester of Mod ontologyTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/11/11</pre>
 */

public class ModOntologyTermTest2 {

    private OlsClient olsClient;

    @Before
    public void parseOboTest() {
        olsClient = new OlsClient();
    }

    @Test
    public void test_synonyms() throws RemoteException {
        // term with shortlabel defined in synonyms
        // it contains 2 aliases
        // this term also have 1 Pubmed and 1 Unimod
        ModOntologyTerm term1 = new ModOntologyTerm("MOD:01161", "deoxygenated residue");
        term1.loadXrefsFrom(olsClient.getTermXrefs("MOD:01161", "MOD"));
        term1.loadSynonymsFrom(olsClient.getTermMetadata("MOD:01161", "MOD"), olsClient.isObsolete("MOD:01161", "MOD"));

        Assert.assertNotNull(term1);

        Assert.assertEquals("deoxygenated residue", term1.getFullName());
        Assert.assertEquals("doxyres", term1.getShortLabel());
        /*Assert.assertEquals(2, term1.getAliases().size());

        for (String alias : term1.getAliases()){
            if (!alias.equals("Deoxy") && !alias.equals("reduction")){
                Assert.assertTrue(false);
            }
        }*/
        Assert.assertEquals(0, term1.getAliases().size());

        //Assert.assertEquals(2, term1.getDbXrefs().size());
        Assert.assertEquals(1, term1.getDbXrefs().size());
        for (TermDbXref xref : term1.getDbXrefs()){
            if (xref.getDatabase().equals("pubmed") && xref.getDatabaseId().equals("MI:0446")){
                if (xref.getQualifier().equals("primary-reference") && xref.getQualifierId().equals("MI:0358")){
                    Assert.assertEquals("14235557", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            }
            /*else if (xref.getDatabase().equals("unimod") && xref.getDatabaseId().equals("MI:1015")){
                if (xref.getQualifier().equals("see-also") && xref.getQualifierId().equals("MI:0361")){
                    Assert.assertEquals("447", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            } */
            else {
                Assert.assertTrue(false);
            }
        }

//        Assert.assertEquals("A protein modification that effectively removes oxygen atoms from a residue without the removal of hydrogen atoms.", term1.getDefinition());

        // term with no alias shortlabel. This term has a comment. This term has also one pubmed xref
        ModOntologyTerm term2 = new ModOntologyTerm("MOD:00003", "UniMod");
        term2.loadXrefsFrom(olsClient.getTermXrefs("MOD:00003", "MOD"));
        term2.loadSynonymsFrom(olsClient.getTermMetadata("MOD:00003", "MOD"), olsClient.isObsolete("MOD:00003", "MOD"));

        Assert.assertNotNull(term2);

        Assert.assertEquals("UniMod", term2.getFullName());
        Assert.assertEquals("unimod", term2.getShortLabel());
        Assert.assertEquals(0, term2.getAliases().size());
//        Assert.assertEquals("Entry from UniMod.", term2.getDefinition());
        Assert.assertEquals(1, term2.getComments().size());
        Assert.assertEquals("This term is for organizational use only and should not be assigned. [JSG]", term2.getComments().iterator().next());

        Assert.assertEquals(1, term2.getDbXrefs().size());
        for (TermDbXref xref : term2.getDbXrefs()){
            if (xref.getDatabase().equals("pubmed") && xref.getDatabaseId().equals("MI:0446")){
                if (xref.getQualifier().equals("primary-reference") && xref.getQualifierId().equals("MI:0358")){
                    Assert.assertEquals("18688235", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            }
            else {
                Assert.assertTrue(false);
            }
        }

        // term with shortlabel defined in synonyms
        // it contains 15 aliases
        // this term also have 5 Pubmeds and 1 resid and 1 deltamass and 1 chebi
        ModOntologyTerm term3 = new ModOntologyTerm("MOD:00014", "L-cysteine residue");
        term3.loadXrefsFrom(olsClient.getTermXrefs("MOD:00014", "MOD"));
        term3.loadSynonymsFrom(olsClient.getTermMetadata("MOD:00014", "MOD"), olsClient.isObsolete("MOD:00014", "MOD"));

        Assert.assertNotNull(term3);

        Assert.assertEquals("L-cysteine residue", term3.getFullName());
        Assert.assertEquals("cys", term3.getShortLabel());
        // Assert.assertEquals(15, term3.getAliases().size());
        Assert.assertEquals(14, term3.getAliases().size());

        for (String alias : term3.getAliases()){
            /*if (!alias.equals("(2R)-2-amino-3-sulfanylpropanoic acid") && !alias.equals("(R)-cysteine") && !alias.equals("2-amino-3-mercaptopropanoic acid")
                    && !alias.equals("2-amino-3-mercaptopropionic acid") && !alias.equals("2-azanyl-3-sulfanylpropanoic acid") && !alias.equals("3-mercapto-L-alanine")
                    && !alias.equals("alpha-amino-beta-mercaptopropanoic acid") && !alias.equals("alpha-amino-beta-mercaptopropionic acid") && !alias.equals("alpha-amino-beta-thiolpropionic acid")
                    && !alias.equals("beta-mercaptoalanine") && !alias.equals("Cysteine (C, Cys)") && !alias.equals("half-cystine")
                    && !alias.equals("L-(+)-cysteine") && !alias.equals("L-cysteine") && !alias.equals("thioserine")){
                Assert.assertTrue(false);
            }*/
            if (!alias.equals("(2R)-2-amino-3-sulfanylpropanoic acid") && !alias.equals("(R)-cysteine") && !alias.equals("2-amino-3-mercaptopropanoic acid")
                    && !alias.equals("2-amino-3-mercaptopropionic acid") && !alias.equals("2-azanyl-3-sulfanylpropanoic acid") && !alias.equals("3-mercapto-L-alanine")
                    && !alias.equals("alpha-amino-beta-mercaptopropanoic acid") && !alias.equals("alpha-amino-beta-mercaptopropionic acid") && !alias.equals("alpha-amino-beta-thiolpropionic acid")
                    && !alias.equals("beta-mercaptoalanine") && !alias.equals("half-cystine")
                    && !alias.equals("L-(+)-cysteine") && !alias.equals("L-cysteine") && !alias.equals("thioserine")){
                Assert.assertTrue(false);
            }
        }

        //Assert.assertEquals(8, term3.getDbXrefs().size());
        Assert.assertEquals(7, term3.getDbXrefs().size());
        for (TermDbXref xref : term3.getDbXrefs()){
            if (xref.getDatabase().equals("pubmed") && xref.getDatabaseId().equals("MI:0446")){
                if (xref.getQualifier().equals("primary-reference") && xref.getQualifierId().equals("MI:0358")){
                    if (!xref.getAccession().equals("1310545") && !xref.getAccession().equals("15790858") && !xref.getAccession().equals("3447159")
                            && !xref.getAccession().equals("6692818") && !xref.getAccession().equals("7338899")){
                        Assert.assertTrue(false);
                    }
                }
                else if (xref.getQualifier().equals("method reference") && xref.getQualifierId().equals("MI:0357")){
                    if (!xref.getAccession().equals("1310545") && !xref.getAccession().equals("15790858") && !xref.getAccession().equals("3447159")
                            && !xref.getAccession().equals("6692818") && !xref.getAccession().equals("7338899")){
                        Assert.assertTrue(false);
                    }
                }
                else{
                    Assert.assertTrue(false);
                }
            }
            /*else if (xref.getDatabase().equals("deltamass") && xref.getDatabaseId().equals("MI:1014")){
                if (xref.getQualifier().equals("see-also") && xref.getQualifierId().equals("MI:0361")){
                    Assert.assertEquals("0", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            }*/
            else if (xref.getDatabase().equals("chebi") && xref.getDatabaseId().equals("MI:0474")){
                if (xref.getQualifier().equals("see-also") && xref.getQualifierId().equals("MI:0361")){
                    Assert.assertEquals("29950", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            }
            else if (xref.getDatabase().equals("resid") && xref.getDatabaseId().equals("MI:0248")){
                if (xref.getQualifier().equals("see-also") && xref.getQualifierId().equals("MI:0361")){
                    Assert.assertEquals("AA0005", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            }
            else {
                Assert.assertTrue(false);
            }
        }

//        Assert.assertEquals("A protein modification that effectively converts a source amino acid residue to an L-cysteine.", term3.getDefinition());
        Assert.assertEquals(1, term3.getComments().size());
        Assert.assertEquals("From DeltaMass: Average Mass: 121.", term3.getComments().iterator().next());

        // term with shortlabel defined in synonyms
        // it contains 3 aliases
        // this term also have 1 Unimod
        ModOntologyTerm term4 = new ModOntologyTerm("MOD:00890", "phosphorylated L-histidine");
        term4.loadXrefsFrom(olsClient.getTermXrefs("MOD:00890", "MOD"));
        term4.loadSynonymsFrom(olsClient.getTermMetadata("MOD:00890", "MOD"), olsClient.isObsolete("MOD:00890", "MOD"));
        Assert.assertNotNull(term4);

        Assert.assertEquals("phosphorylated L-histidine", term4.getFullName());
        Assert.assertEquals("nphoshis", term4.getShortLabel());
        //Assert.assertEquals(3, term4.getAliases().size());
        Assert.assertEquals(1, term4.getAliases().size());

        for (String alias : term4.getAliases()){
            /*if (!alias.equals("Phospho") && !alias.equals("phosphohistidine") && !alias.equals("mod192")){
                Assert.assertTrue(false);
            }*/
            if (!alias.equals("phosphohistidine")){
                Assert.assertTrue(false);
            }
        }

        //Assert.assertEquals(1, term4.getDbXrefs().size());
        Assert.assertEquals(0, term4.getDbXrefs().size());
        /*for (TermDbXref xref : term4.getDbXrefs()){
            if (xref.getDatabase().equals("unimod") && xref.getDatabaseId().equals("MI:1015")){
                if (xref.getQualifier().equals("see-also") && xref.getQualifierId().equals("MI:0361")){
                    Assert.assertEquals("21", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            }
            else {
                Assert.assertTrue(false);
            }
        } */

//        Assert.assertEquals("A protein modification that effectively converts an L-histidine residue to a phosphorylated L-histidine, such as pros-phosphohistidine, or tele-phosphohistidine.", term4.getDefinition());
    }

    @Test
    public void test_xref_definition() throws RemoteException {
        // term with shortlabel not defined in synonyms
        // it contains URL xref
        ModOntologyTerm term5 = new ModOntologyTerm("MOD:01603", "2x(15)N labeled L-lysine");
        term5.loadXrefsFrom(olsClient.getTermXrefs("MOD:01603", "MOD"));
        term5.loadSynonymsFrom(olsClient.getTermMetadata("MOD:01603", "MOD"), olsClient.isObsolete("MOD:01603", "MOD"));
        Assert.assertNotNull(term5);

        Assert.assertEquals("2x(15)N labeled L-lysine", term5.getFullName());
        Assert.assertEquals("2x(15)n labeled l-lysine", term5.getShortLabel());
        Assert.assertEquals(0, term5.getAliases().size());

        Assert.assertEquals(1, term5.getDbXrefs().size());
        for (TermDbXref xref : term5.getDbXrefs()){
            if (xref.getDatabase().equals("pubmed") && xref.getDatabaseId().equals("MI:0446")){
                if (xref.getQualifier().equals("primary-reference") && xref.getQualifierId().equals("MI:0358")){
                    Assert.assertEquals("18688235", xref.getAccession());
                }
                else{
                    Assert.assertTrue(false);
                }
            }
            else {
                Assert.assertTrue(false);
            }
        }
        Assert.assertEquals("http://www.sigmaaldrich.com/catalog/ProductDetail.do?N4=609021|ALDRICH&N5=SEARCH_CONCAT_PNO|BRAND_KEY&F=SPEC&lang=en_US0.000000E+000", term5.getURL());
//        Assert.assertEquals("A protein modification that effectively converts an L-lysine residue to 2x(15)N labeled L-lysine.", term5.getDefinition());
    }

    @Test
    public void test_obsolete() throws RemoteException {
        // term having one obsolete 'remap to'
        ModOntologyTerm term1 = new ModOntologyTerm("MOD:00815");
        term1.loadXrefsFrom(olsClient.getTermXrefs("MOD:00815", "MOD"));
        term1.loadSynonymsFrom(olsClient.getTermMetadata("MOD:00815", "MOD"), olsClient.isObsolete("MOD:00815", "MOD"));

        Assert.assertNotNull(term1);

//        Assert.assertEquals("", term1.getDefinition());
//        Assert.assertEquals("OBSOLETE because redundant with MOD:00151. Remap to MOD:00151.", term1.getObsoleteMessage());
        Assert.assertEquals(0, term1.getPossibleTermsToRemapTo().size());
//        Assert.assertEquals("MOD:00151", term1.getRemappedTerm());

        // term having one obsolete 'map to'
        ModOntologyTerm term2 = new ModOntologyTerm("MOD:01292");
        term2.loadXrefsFrom(olsClient.getTermXrefs("MOD:01292", "MOD"));
        term2.loadSynonymsFrom(olsClient.getTermMetadata("MOD:01292", "MOD"), olsClient.isObsolete("MOD:01292", "MOD"));
        Assert.assertNotNull(term2);

//        Assert.assertEquals("", term2.getDefinition());
//        Assert.assertEquals("OBSOLETE because redundant and identical to MOD:00075. Map to MOD:00075.", term2.getObsoleteMessage());
//        Assert.assertEquals(0, term2.getPossibleTermsToRemapTo().size());
//        Assert.assertEquals("MOD:00075", term2.getRemappedTerm());

        // term having one obsolete and several choices after map to
        ModOntologyTerm term4 = new ModOntologyTerm("MOD:00564");
        term4.loadXrefsFrom(olsClient.getTermXrefs("MOD:00564", "MOD"));
        term4.loadSynonymsFrom(olsClient.getTermMetadata("MOD:00564", "MOD"), olsClient.isObsolete("MOD:00564", "MOD"));

        Assert.assertNotNull(term4);

//        Assert.assertEquals("Modification from UniMod Isotopic label. The UniMod term was extracted when it had not been approved. ", term4.getDefinition());
//        Assert.assertEquals("OBSOLETE because redundant to MOD:01505. Remap to MOD:01505, or one of the child terms MOD:01493 or MOD:01497.", term4.getObsoleteMessage());
//        Assert.assertEquals(3, term4.getPossibleTermsToRemapTo().size());
//        for (String term : term4.getPossibleTermsToRemapTo()){
//            if (!term.equalsIgnoreCase("MOD:01505") && !term.equalsIgnoreCase("MOD:01493") && !term.equalsIgnoreCase("MOD:01497")){
//                Assert.assertFalse(true);
//            }
//        }
//        Assert.assertNull(term4.getRemappedTerm());

        // term having one obsolete and no choices
        ModOntologyTerm term5 = new ModOntologyTerm("MOD:00632");
        term5.loadXrefsFrom(olsClient.getTermXrefs("MOD:00632", "MOD"));
        term5.loadSynonymsFrom(olsClient.getTermMetadata("MOD:00632", "MOD"), olsClient.isObsolete("MOD:00632", "MOD"));

        Assert.assertNotNull(term5);

//        Assert.assertEquals("", term5.getDefinition());
//        Assert.assertEquals("OBSOLETE because this chemical derivative modification from UniMod 321 is deprecated.", term5.getObsoleteMessage());
//        Assert.assertEquals(0, term5.getPossibleTermsToRemapTo().size());
//        Assert.assertNull(term5.getRemappedTerm());
    }
}
