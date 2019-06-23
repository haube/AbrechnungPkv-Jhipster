import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IArzt } from '@/shared/model/arzt.model';

const baseApiUrl = 'api/arzts';

export default class ArztService {
  public find(id): Promise<IArzt> {
    return new Promise<IArzt>(resolve => {
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

  public create(entity): Promise<IArzt> {
    return new Promise<IArzt>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity): Promise<IArzt> {
    return new Promise<IArzt>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
