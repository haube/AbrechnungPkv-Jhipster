<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('pkvApp.arzt.home.title')" id="arzt-heading">Arzts</span>
            <router-link :to="{name: 'ArztCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-arzt">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('pkvApp.arzt.home.createLabel')">
                    Create new Arzt
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="table-responsive" v-if="arzts">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('pkvApp.arzt.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('strasse')"><span v-text="$t('pkvApp.arzt.strasse')">Strasse</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('hausnummer')"><span v-text="$t('pkvApp.arzt.hausnummer')">Hausnummer</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('plz')"><span v-text="$t('pkvApp.arzt.plz')">Plz</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ort')"><span v-text="$t('pkvApp.arzt.ort')">Ort</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('telefon')"><span v-text="$t('pkvApp.arzt.telefon')">Telefon</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('telefon2')"><span v-text="$t('pkvApp.arzt.telefon2')">Telefon 2</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fax')"><span v-text="$t('pkvApp.arzt.fax')">Fax</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('email')"><span v-text="$t('pkvApp.arzt.email')">Email</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('web')"><span v-text="$t('pkvApp.arzt.web')">Web</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="arzt of orderBy(arzts, propOrder, reverse === true ? 1 : -1)"
                    :key="arzt.id">
                    <td>
                        <router-link :to="{name: 'ArztView', params: {arztId: arzt.id}}">{{arzt.id}}</router-link>
                    </td>
                    <td>{{arzt.name}}</td>
                    <td>{{arzt.strasse}}</td>
                    <td>{{arzt.hausnummer}}</td>
                    <td>{{arzt.plz}}</td>
                    <td>{{arzt.ort}}</td>
                    <td>{{arzt.telefon}}</td>
                    <td>{{arzt.telefon2}}</td>
                    <td>{{arzt.fax}}</td>
                    <td>{{arzt.email}}</td>
                    <td>{{arzt.web}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'ArztView', params: {arztId: arzt.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ArztEdit', params: {arztId: arzt.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(arzt)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="pkvApp.arzt.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-arzt-heading" v-bind:title="$t('pkvApp.arzt.delete.question')">Are you sure you want to delete this Arzt?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-arzt" v-text="$t('entity.action.delete')" v-on:click="removeArzt()">Delete</button>
            </div>
        </b-modal>
        <div v-if="arzts && arzts.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./arzt.component.ts">
</script>
