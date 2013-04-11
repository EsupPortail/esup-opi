package org.esupportail.opi.utils.fj;

import static fj.data.IterableW.wrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuFormId;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.DomaineAnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.formation.GrpTypDipCorresp;

import fj.F;
import static fj.data.Stream.*;
import static fj.P.*;
import fr.univ.rennes1.cri.apogee.domain.beans.ArrayOfRen1GrpTypDipCorresp;
import fr.univ.rennes1.cri.apogee.domain.beans.Ren1GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.beans.Ren1GrpTypDipCorresp;
import fr.univ.rennes1.cri.apogee.domain.dto.Ren1Cles2AnnuFormDTO;
import fr.univ.rennes1.cri.apogee.domain.dto.Ren1Domaine2AnnuFormDTO;

public class Conversions {

    public static final F<String, Boolean> parseBoolean = new F<String, Boolean>() {
        public Boolean f(String s) {
            return Boolean.parseBoolean(s);
        }
    };
	
	/**
	 * A {@link F}unction that converts a {@link Ren1GrpTypDipCorresp} to a
	 * {@link GrpTypDipCorresp}
	 */
	public static final F<Ren1GrpTypDipCorresp, GrpTypDipCorresp> toGrpTypDipCorresp =
			new F<Ren1GrpTypDipCorresp, GrpTypDipCorresp>() {
				public GrpTypDipCorresp f(final Ren1GrpTypDipCorresp r1gc) {
					return new GrpTypDipCorresp(r1gc.getCodGrpTpd(), r1gc.getCodTpdEtb());
				}		
	};
	
	/**
	 * A {@link F}unction that converts a {@link GrpTypDipCorresp} to a
	 * {@link Ren1GrpTypDipCorresp}
	 */
	public static final F<GrpTypDipCorresp, Ren1GrpTypDipCorresp> toR1GrpTypDipCorresp =
			new F<GrpTypDipCorresp, Ren1GrpTypDipCorresp>() {
				public Ren1GrpTypDipCorresp f(final GrpTypDipCorresp g) {
					return new Ren1GrpTypDipCorresp()
					.withCodGrpTpd(g.getGrpTpd().getCodGrpTpd())
					.withCodTpdEtb(g.getCodTpdEtb());
				}
			};
	
	/**
	 * A {@link F}unction that converts a {@link Ren1GrpTypDip} to a
	 * {@link GrpTypDip} 
	 */
	public static final F<Ren1GrpTypDip, GrpTypDip> toGrpTypDip =
		new F<Ren1GrpTypDip, GrpTypDip>() {
		    public GrpTypDip f(final Ren1GrpTypDip r1g) {
		    	return new GrpTypDip(
		    			r1g.getCodGrpTpd(),
		    			r1g.getLibGrpTpd(),
		    			r1g.getTemEnSveGrpTpd(),
		    			new HashSet<GrpTypDipCorresp>(
                                wrap(r1g.getRen1GrpTypDipCorresps().getRen1GrpTypDipCorresps())
                                        .map(toGrpTypDipCorresp).toStandardList()));
		    }
	};
	
	/**
	 * A {@link F}unction that converts a {@link GrpTypDip} to a
	 * {@link Ren1GrpTypDip}
	 */
	public static final F<GrpTypDip, Ren1GrpTypDip> toR1GrpTypDip =
		new F<GrpTypDip, Ren1GrpTypDip>() {
		    public Ren1GrpTypDip f(GrpTypDip g) {
		    	return new Ren1GrpTypDip()
		    	.withCodGrpTpd(g.getCodGrpTpd())
		    	.withLibGrpTpd(g.getLibGrpTpd())
		    	.withTemEnSveGrpTpd(g.getTemEnSveGrpTpd())
		    	.withRen1GrpTypDipCorresps(
		    			new ArrayOfRen1GrpTypDipCorresp()
		    			.withRen1GrpTypDipCorresps(
		    					wrap(g.getGrpTypDipCorresps())
                                        .map(toR1GrpTypDipCorresp)
                                        .toStandardList()));
		    }
	};

	/**
	 * A {@link F}unction that converts a {@link Ren1Domaine2AnnuFormDTO} to a
	 * {@link Domaine2AnnuForm}
	 */
	public static final F<Ren1Domaine2AnnuFormDTO, Domaine2AnnuForm> toDomaine2AnnuForm =
			new F<Ren1Domaine2AnnuFormDTO, Domaine2AnnuForm>() {
				public Domaine2AnnuForm f(Ren1Domaine2AnnuFormDTO r1d) {
					return new Domaine2AnnuForm(
							r1d.getCodLang(),
							r1d.getCodDom(),
							r1d.getLibDom(),
							new DomaineAnnuForm(r1d.getCodDom(),
									r1d.getTemSveDom()));
				}
			};
	/**
	 * A {@link F}unction that converts a {@link Ren1Domaine2AnnuFormDTO} to a
	 * map of {@link Domaine2AnnuForm} with its {@link Cles2AnnuForm}
	 */
	public static final F<Ren1Domaine2AnnuFormDTO,
            Map<Domaine2AnnuForm,List<Cles2AnnuForm>>> toMapDomaine2AnnuForm =
			new F<Ren1Domaine2AnnuFormDTO, Map<Domaine2AnnuForm,List<Cles2AnnuForm>>>() {

				@Override
				public Map<Domaine2AnnuForm, List<Cles2AnnuForm>> f(final Ren1Domaine2AnnuFormDTO d) {
					return new HashMap<Domaine2AnnuForm, List<Cles2AnnuForm>>() {
						{
							put(p(d).map(toDomaine2AnnuForm)._1(),
                                    new ArrayList<Cles2AnnuForm>(
                                            iterableStream(
                                                    d.getRen1Cles2AnnuFormDTO()
                                                            .getRen1Cles2AnnuFormDTOs())
                                                    .map(toCles2AnnuForm(d.getCodDom()))
                                                    .toCollection()));
                        }};
                }
			};
	
	public static final F<Ren1Cles2AnnuFormDTO, Cles2AnnuForm> toCles2AnnuForm(final String codDom){
			return new F<Ren1Cles2AnnuFormDTO, Cles2AnnuForm>() {
				public Cles2AnnuForm f(Ren1Cles2AnnuFormDTO r1c) {
					return new Cles2AnnuForm(
							new Cles2AnnuFormId(r1c.getCodLang(), r1c.getCodCles()),
							new ClesAnnuForm(r1c.getCodCles(), codDom, r1c.getTemSveCles()),
							r1c.getLibCles());
				}
			};
	}
	
	public static final F<Map<Domaine2AnnuForm,List<Cles2AnnuForm>>,
            F<Map<Domaine2AnnuForm,List<Cles2AnnuForm>>,Map<Domaine2AnnuForm,List<Cles2AnnuForm>>>> toMapDomCles =
		new F<Map<Domaine2AnnuForm,List<Cles2AnnuForm>>, F<Map<Domaine2AnnuForm,List<Cles2AnnuForm>>,Map<Domaine2AnnuForm,List<Cles2AnnuForm>>>>() {
		@Override
		public F<Map<Domaine2AnnuForm, List<Cles2AnnuForm>>, Map<Domaine2AnnuForm, List<Cles2AnnuForm>>> f(
				final Map<Domaine2AnnuForm, List<Cles2AnnuForm>> map1) {
			return new F<Map<Domaine2AnnuForm, List<Cles2AnnuForm>>, Map<Domaine2AnnuForm, List<Cles2AnnuForm>>>() {

				@Override
				public Map<Domaine2AnnuForm, List<Cles2AnnuForm>> f(
						Map<Domaine2AnnuForm, List<Cles2AnnuForm>> map2) {
					map1.putAll(map2);
					return map1;
				}
				
			};
		}
	};
}
