import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ITermin } from '@/shared/model/termin.model';

const baseApiUrl = 'api/termins';

export default class TerminService {
  public find(id): Promise<ITermin> {
    return new Promise<ITermin>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity): Promise<ITermin> {
    return new Promise<ITermin>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity): Promise<ITermin> {
    return new Promise<ITermin>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
