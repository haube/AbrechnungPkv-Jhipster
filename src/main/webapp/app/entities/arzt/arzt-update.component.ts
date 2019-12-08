import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import TerminService from '../termin/termin.service';
import { ITermin } from '@/shared/model/termin.model';

import RechnungService from '../rechnung/rechnung.service';
import { IRechnung } from '@/shared/model/rechnung.model';

import AlertService from '@/shared/alert/alert.service';
import { IArzt, Arzt } from '@/shared/model/arzt.model';
import ArztService from './arzt.service';

const validations: any = {
  arzt: {
    name: {
      required
    },
    strasse: {},
    hausnummer: {},
    plz: {},
    ort: {},
    telefon: {},
    telefon2: {},
    fax: {},
    email: {},
    web: {}
  }
};

@Component({
  validations
})
export default class ArztUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('arztService') private arztService: () => ArztService;
  public arzt: IArzt = new Arzt();

  @Inject('terminService') private terminService: () => TerminService;

  public termins: ITermin[] = [];

  @Inject('rechnungService') private rechnungService: () => RechnungService;

  public rechnungs: IRechnung[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.arztId) {
        vm.retrieveArzt(to.params.arztId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.arzt.id) {
      this.arztService()
        .update(this.arzt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pkvApp.arzt.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.arztService()
        .create(this.arzt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pkvApp.arzt.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveArzt(arztId): void {
    this.arztService()
      .find(arztId)
      .then(res => {
        this.arzt = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.terminService()
      .retrieve()
      .then(res => {
        this.termins = res.data;
      });
    this.rechnungService()
      .retrieve()
      .then(res => {
        this.rechnungs = res.data;
      });
  }
}
