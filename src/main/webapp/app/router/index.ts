import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const Arzt = () => import('../entities/arzt/arzt.vue');
// prettier-ignore
const ArztUpdate = () => import('../entities/arzt/arzt-update.vue');
// prettier-ignore
const ArztDetails = () => import('../entities/arzt/arzt-details.vue');
// prettier-ignore
const Termin = () => import('../entities/termin/termin.vue');
// prettier-ignore
const TerminUpdate = () => import('../entities/termin/termin-update.vue');
// prettier-ignore
const TerminDetails = () => import('../entities/termin/termin-details.vue');
// prettier-ignore
const Rechnung = () => import('../entities/rechnung/rechnung.vue');
// prettier-ignore
const RechnungUpdate = () => import('../entities/rechnung/rechnung-update.vue');
// prettier-ignore
const RechnungDetails = () => import('../entities/rechnung/rechnung-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
    ,
    {
      path: '/entity/arzt',
      name: 'Arzt',
      component: Arzt,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/arzt/new',
      name: 'ArztCreate',
      component: ArztUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/arzt/:arztId/edit',
      name: 'ArztEdit',
      component: ArztUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/arzt/:arztId/view',
      name: 'ArztView',
      component: ArztDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/termin',
      name: 'Termin',
      component: Termin,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/termin/new',
      name: 'TerminCreate',
      component: TerminUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/termin/:terminId/edit',
      name: 'TerminEdit',
      component: TerminUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/termin/:terminId/view',
      name: 'TerminView',
      component: TerminDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/rechnung',
      name: 'Rechnung',
      component: Rechnung,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/rechnung/new',
      name: 'RechnungCreate',
      component: RechnungUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/rechnung/:rechnungId/edit',
      name: 'RechnungEdit',
      component: RechnungUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/rechnung/:rechnungId/view',
      name: 'RechnungView',
      component: RechnungDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
