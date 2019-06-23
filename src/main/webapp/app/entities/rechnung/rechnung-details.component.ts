import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRechnung } from '@/shared/model/rechnung.model';
import RechnungService from './rechnung.service';

@Component
export default class RechnungDetails extends Vue {
  @Inject('rechnungService') private rechnungService: () => RechnungService;
  public rechnung: IRechnung = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rechnungId) {
        vm.retrieveRechnung(to.params.rechnungId);
      }
    });
  }

  public retrieveRechnung(rechnungId) {
    this.rechnungService()
      .find(rechnungId)
      .then(res => {
        this.rechnung = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
