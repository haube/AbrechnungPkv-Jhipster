import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRechnung } from '@/shared/model/rechnung.model';

const baseApiUrl = 'api/rechnungs';

export default class RechnungService {
  public find(id): Promise<IRechnung> {
    return new Promise<IRechnung>(resolve => {
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

  public create(entity): Promise<IRechnung> {
    return new Promise<IRechnung>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity): Promise<IRechnung> {
    return new Promise<IRechnung>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
