<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="pkvApp.arzt.home.createOrEditLabel" v-text="$t('pkvApp.arzt.home.createOrEditLabel')">Create or edit a Arzt</h2>
                <div>
                    <div class="form-group" v-if="arzt.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="arzt.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.name')" for="arzt-name">Name</label>
                        <input type="text" class="form-control" name="name" id="arzt-name"
                            :class="{'valid': !$v.arzt.name.$invalid, 'invalid': $v.arzt.name.$invalid }" v-model="$v.arzt.name.$model"  required/>
                        <div v-if="$v.arzt.name.$anyDirty && $v.arzt.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.arzt.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.strasse')" for="arzt-strasse">Strasse</label>
                        <input type="text" class="form-control" name="strasse" id="arzt-strasse"
                            :class="{'valid': !$v.arzt.strasse.$invalid, 'invalid': $v.arzt.strasse.$invalid }" v-model="$v.arzt.strasse.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.hausnummer')" for="arzt-hausnummer">Hausnummer</label>
                        <input type="text" class="form-control" name="hausnummer" id="arzt-hausnummer"
                            :class="{'valid': !$v.arzt.hausnummer.$invalid, 'invalid': $v.arzt.hausnummer.$invalid }" v-model="$v.arzt.hausnummer.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.plz')" for="arzt-plz">Plz</label>
                        <input type="text" class="form-control" name="plz" id="arzt-plz"
                            :class="{'valid': !$v.arzt.plz.$invalid, 'invalid': $v.arzt.plz.$invalid }" v-model="$v.arzt.plz.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.ort')" for="arzt-ort">Ort</label>
                        <input type="text" class="form-control" name="ort" id="arzt-ort"
                            :class="{'valid': !$v.arzt.ort.$invalid, 'invalid': $v.arzt.ort.$invalid }" v-model="$v.arzt.ort.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.telefon')" for="arzt-telefon">Telefon</label>
                        <input type="text" class="form-control" name="telefon" id="arzt-telefon"
                            :class="{'valid': !$v.arzt.telefon.$invalid, 'invalid': $v.arzt.telefon.$invalid }" v-model="$v.arzt.telefon.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.telefon2')" for="arzt-telefon2">Telefon 2</label>
                        <input type="text" class="form-control" name="telefon2" id="arzt-telefon2"
                            :class="{'valid': !$v.arzt.telefon2.$invalid, 'invalid': $v.arzt.telefon2.$invalid }" v-model="$v.arzt.telefon2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.fax')" for="arzt-fax">Fax</label>
                        <input type="text" class="form-control" name="fax" id="arzt-fax"
                            :class="{'valid': !$v.arzt.fax.$invalid, 'invalid': $v.arzt.fax.$invalid }" v-model="$v.arzt.fax.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.email')" for="arzt-email">Email</label>
                        <input type="text" class="form-control" name="email" id="arzt-email"
                            :class="{'valid': !$v.arzt.email.$invalid, 'invalid': $v.arzt.email.$invalid }" v-model="$v.arzt.email.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.arzt.web')" for="arzt-web">Web</label>
                        <input type="text" class="form-control" name="web" id="arzt-web"
                            :class="{'valid': !$v.arzt.web.$invalid, 'invalid': $v.arzt.web.$invalid }" v-model="$v.arzt.web.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.arzt.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./arzt-update.component.ts">
</script>
