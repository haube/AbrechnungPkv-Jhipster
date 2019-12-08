import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITermin } from '@/shared/model/termin.model';
import TerminService from './termin.service';

@Component
export default class TerminDetails extends Vue {
  @Inject('terminService') private terminService: () => TerminService;
  public termin: ITermin = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.terminId) {
        vm.retrieveTermin(to.params.terminId);
      }
    });
  }

  public retrieveTermin(terminId) {
    this.terminService()
      .find(terminId)
      .then(res => {
        this.termin = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
