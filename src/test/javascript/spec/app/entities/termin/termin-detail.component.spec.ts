/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import TerminDetailComponent from '@/entities/termin/termin-details.vue';
import TerminClass from '@/entities/termin/termin-details.component';
import TerminService from '@/entities/termin/termin.service';

const localVue = createLocalVue();
const mockedAxios: any = axios;

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

jest.mock('axios', () => ({
  get: jest.fn()
}));

describe('Component Tests', () => {
  describe('Termin Management Detail Component', () => {
    let wrapper: Wrapper<TerminClass>;
    let comp: TerminClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      wrapper = shallowMount<TerminClass>(TerminDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { terminService: () => new TerminService() }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', async () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

        // WHEN
        comp.retrieveTermin(123);
        await comp.$nextTick();

        // THEN
        expect(comp.termin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
