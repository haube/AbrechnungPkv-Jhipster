import { mixins } from 'vue-class-component';
import { Component, Inject, Vue } from 'vue-property-decorator';
import { IRechnung } from '@/shared/model/rechnung.model';
import AlertService from '@/shared/alert/alert.service';

import RechnungService from './rechnung.service';

@Component
export default class Rechnung extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rechnungService') private rechnungService: () => RechnungService;
  private removeId: string = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage: number = null;
  public propOrder = 'id';
  public reverse = true;
  public totalItems = 0;
  public rechnungs: IRechnung[] = [];

  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllRechnungs();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRechnungs();
  }

  public retrieveAllRechnungs(): void {
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.rechnungService()
      .retrieve(paginationQuery)
      .then(res => {
        this.rechnungs = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
      });
  }

  public prepareRemove(instance): void {
    this.removeId = instance.id;
  }

  public removeRechnung(): void {
    this.rechnungService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('pkvApp.rechnung.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllRechnungs();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllRechnungs();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
