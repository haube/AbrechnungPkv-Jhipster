import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT, INSTANT_FORMAT, ZONED_DATE_TIME_FORMAT } from '@/shared/date/filters';

import ArztService from '../arzt/arzt.service';
import { IArzt } from '@/shared/model/arzt.model';

import AlertService from '@/shared/alert/alert.service';
import { ITermin, Termin } from '@/shared/model/termin.model';
import TerminService from './termin.service';

const validations: any = {
  termin: {
    datum: {
      required
    },
    notiz: {}
  }
};

@Component({
  validations
})
export default class TerminUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('terminService') private terminService: () => TerminService;
  public termin: ITermin = new Termin();

  @Inject('arztService') private arztService: () => ArztService;

  public arzts: IArzt[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.terminId) {
        vm.retrieveTermin(to.params.terminId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.termin.id) {
      this.terminService()
        .update(this.termin)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pkvApp.termin.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.terminService()
        .create(this.termin)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pkvApp.termin.created', { param: param.id });
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
      this.termin[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.termin[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.termin[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.termin[field] = null;
    }
  }

  public retrieveTermin(terminId): void {
    this.terminService()
      .find(terminId)
      .then(res => {
        this.termin = res;
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
