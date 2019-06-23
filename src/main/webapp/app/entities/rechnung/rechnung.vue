<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('pkvApp.rechnung.home.title')" id="rechnung-heading">Rechnungs</span>
            <router-link :to="{name: 'RechnungCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-rechnung">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('pkvApp.rechnung.home.createLabel')">
                    Create new Rechnung
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
        <div class="table-responsive" v-if="rechnungs">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('betrag')"><span v-text="$t('pkvApp.rechnung.betrag')">Betrag</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('datumRechnung')"><span v-text="$t('pkvApp.rechnung.datumRechnung')">Datum Rechnung</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('datumZahlung')"><span v-text="$t('pkvApp.rechnung.datumZahlung')">Datum Zahlung</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('arzt.id')"><span v-text="$t('pkvApp.rechnung.arzt')">Arzt</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="rechnung of orderBy(rechnungs, propOrder, reverse === true ? 1 : -1)"
                    :key="rechnung.id">
                    <td>
                        <router-link :to="{name: 'RechnungView', params: {rechnungId: rechnung.id}}">{{rechnung.id}}</router-link>
                    </td>
                    <td>{{rechnung.betrag}}</td>
                    <td>{{rechnung.datumRechnung | formatDate}}</td>
                    <td>{{rechnung.datumZahlung | formatDate}}</td>
                    <td>
                        <div v-if="rechnung.arzt">
                            <router-link :to="{name: 'ArztView', params: {arztId: rechnung.arzt.id}}">{{rechnung.arzt.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'RechnungView', params: {rechnungId: rechnung.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RechnungEdit', params: {rechnungId: rechnung.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(rechnung)"
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
            <span slot="modal-title"><span id="pkvApp.rechnung.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-rechnung-heading" v-bind:title="$t('pkvApp.rechnung.delete.question')">Are you sure you want to delete this Rechnung?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-rechnung" v-text="$t('entity.action.delete')" v-on:click="removeRechnung()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./rechnung.component.ts">
</script>
