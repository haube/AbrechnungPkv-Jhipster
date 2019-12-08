<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="pkvApp.rechnung.home.createOrEditLabel" v-text="$t('pkvApp.rechnung.home.createOrEditLabel')">Create or edit a Rechnung</h2>
                <div>
                    <div class="form-group" v-if="rechnung.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rechnung.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.rechnung.betrag')" for="rechnung-betrag">Betrag</label>
                        <input type="number" class="form-control" name="betrag" id="rechnung-betrag"
                            :class="{'valid': !$v.rechnung.betrag.$invalid, 'invalid': $v.rechnung.betrag.$invalid }" v-model.number="$v.rechnung.betrag.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.rechnung.datumRechnung')" for="rechnung-datumRechnung">Datum Rechnung</label>
                        <div class="d-flex">
                            <input id="rechnung-datumRechnung" type="datetime-local" class="form-control" name="datumRechnung" :class="{'valid': !$v.rechnung.datumRechnung.$invalid, 'invalid': $v.rechnung.datumRechnung.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.rechnung.datumRechnung.$model)"
                            @change="updateInstantField('datumRechnung', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.rechnung.datumZahlung')" for="rechnung-datumZahlung">Datum Zahlung</label>
                        <div class="d-flex">
                            <input id="rechnung-datumZahlung" type="datetime-local" class="form-control" name="datumZahlung" :class="{'valid': !$v.rechnung.datumZahlung.$invalid, 'invalid': $v.rechnung.datumZahlung.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.rechnung.datumZahlung.$model)"
                            @change="updateInstantField('datumZahlung', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('pkvApp.rechnung.arzt')" for="rechnung-arzt">Arzt</label>
                        <select class="form-control" id="rechnung-arzt" name="arzt" v-model="rechnung.arzt" >
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rechnung.arzt && arztOption.id === rechnung.arzt.id ? rechnung.arzt : arztOption" v-for="arztOption in arzts" :key="arztOption.id">{{arztOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rechnung.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./rechnung-update.component.ts">
</script>
