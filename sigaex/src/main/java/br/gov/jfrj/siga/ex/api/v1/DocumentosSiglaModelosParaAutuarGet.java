package br.gov.jfrj.siga.ex.api.v1;

import java.util.ArrayList;
import java.util.List;

import br.gov.jfrj.siga.dp.DpLotacao;
import br.gov.jfrj.siga.dp.DpPessoa;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.ExModelo;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentosSiglaModelosParaAutuarGetRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentosSiglaModelosParaAutuarGetResponse;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IDocumentosSiglaModelosParaAutuarGet;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.ModeloItem;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.vraptor.SigaObjects;

public class DocumentosSiglaModelosParaAutuarGet implements IDocumentosSiglaModelosParaAutuarGet {

	@Override
	public String getContext() {
		return "obter lista de modelos";
	}

	@Override
	public void run(DocumentosSiglaModelosParaAutuarGetRequest req, DocumentosSiglaModelosParaAutuarGetResponse resp)
			throws Exception {
		boolean isEditandoAnexo = false;
		boolean isCriandoSubprocesso = false;
		ExMobil mobPai = null;
		String headerValue = null;
		boolean isAutuando = true;

		try (ApiContext ctx = new ApiContext(false, true)) {
			ApiContext.assertAcesso("");
			SigaObjects so = ApiContext.getSigaObjects();

			DpPessoa titular = so.getTitular();
			DpLotacao lotaTitular = so.getLotaTitular();
			List<ExModelo> modelos = Ex.getInstance().getBL().obterListaModelos(null, null, isEditandoAnexo,
					isCriandoSubprocesso, mobPai, headerValue, true, titular, lotaTitular, isAutuando);

			resp.list = new ArrayList<>();
			for (ExModelo m : modelos) {
				ModeloItem mi = new ModeloItem();
				mi.idModelo = m.getId().toString();
				mi.nome = m.getNmMod();
				mi.descr = m.getDescMod();
				resp.list.add(mi);
			}
		}
	}

}
