import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import ArztService from '../arzt/arzt.service';
import { IArzt } from '@/shared/model/arzt.model';

import AlertService from '@/shared/alert/alert.service';
import { IRechnung, Rechnung } from '@/shared/model/rechnung.model';
import RechnungService from './rechnung.service';

const validations: any = {
  rechnung: {
    betrag: {},
    datumRechnung: {},
    datumZahlung: {}
  }
};

@Component({
  validations
})
export default class RechnungUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rechnungService') private rechnungService: () => RechnungService;
  public rechnung: IRechnung = new Rechnung();

  @Inject('arztService') private arztService: () => ArztService;

  public arzts: IArzt[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rechnungId) {
        vm.retrieveRechnung(to.params.rechnungId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.rechnung.id) {
      this.rechnungService()
        .update(this.rechnung)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pkvApp.rechnung.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rechnungService()
        .create(this.rechnung)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pkvApp.rechnung.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.rechnung[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.rechnung[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.rechnung[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.rechnung[field] = null;
    }
  }

  public retrieveRechnung(rechnungId): void {
    this.rechnungService()
      .find(rechnungId)
      .then(res => {
        this.rechnung = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.arztService()
      .retrieve()
      .then(res => {
        this.arzts = res.data;
      });
  }
}
