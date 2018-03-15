package fr.cnamts.prototype.aat.service;

import java.io.File;
import java.util.List;

import fr.cnamts.prototype.aat.entity.Motif;


/**
 * Service metier pour l'indexation de la recherche.
 *<p>
 * Description : Ce service permet de faire l'indexation en memoire
 * ou sur disque du référentiel CSV des motif AAT, et de lancer la
 * recherche des motifs synonymes.
 *</p>
 * @author ONDONGO-09929
 *
 */
public interface LuceneIndexRecherche {
    /**
     * Méthode permettant de faire l'indexation en memoire
     * du référentiel passé en paramètre.
     * @param pReferentielMotifAAT : le référentiel.
     * @throws TechniqueException : Notification d'une erreur
     */
    void indexationMemoire(final File pReferentielMotifAAT);

    /**
     * Méthode permettant de faire l'indexation en memoire
     * du référentiel passé en paramètre.
     * @param pReferentielMotifAAT : le référentiel.
     * @throws TechniqueException : Notification d'une erreur
     */
    void indexationMemoire(final List < Motif > pReferentielMotifAAT);

    /**
     * Méthode permettant de faire l'indexation en memoire
     * du référentiel passé en paramètre.
     * @param pReferentielMotifAAT : le référentiel.
     * @param pDisquePath : le repertoire disque de stockage.
     * @throws TechniqueException : Notification d'une erreur
     */
    void indexationDisque(final File pReferentielMotifAAT,
            final File pDisquePath);

    /**
     * Méthode permettant la recherche d'un ou plusieur motif.
     * @param pLibelleSaisie : la saisie du PS.
     * @param pHeaderDesirTO : contexte DESIR
     * @return : la liste des motifs correspondant.
     * @throws TechniqueException : Notification d'une erreur
     */
    List < Motif > rechercher(final String pLibelleSaisie);
}
