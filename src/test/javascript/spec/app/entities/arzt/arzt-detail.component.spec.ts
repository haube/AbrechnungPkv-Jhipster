/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import ArztDetailComponent from '@/entities/arzt/arzt-details.vue';
import ArztClass from '@/entities/arzt/arzt-details.component';
import ArztService from '@/entities/arzt/arzt.service';

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
  describe('Arzt Management Detail Component', () => {
    let wrapper: Wrapper<ArztClass>;
    let comp: ArztClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      wrapper = shallowMount<ArztClass>(ArztDetailComponent, { store, i18n, localVue, provide: { arztService: () => new ArztService() } });
      comp = wrapper.vm;
    });

    describe('OnInit', async () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

        // WHEN
        comp.retrieveArzt(123);
        await comp.$nextTick();

        // THEN
        expect(comp.arzt).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
