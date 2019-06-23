/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import RechnungService from '@/entities/rechnung/rechnung.service';
import { Rechnung } from '@/shared/model/rechnung.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Rechnung Service', () => {
    let service: RechnungService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new RechnungService();
      currentDate = new Date();

      elemDefault = new Rechnung(0, 0, currentDate, currentDate);
    });

    describe('Service methods', async () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            datumRechnung: format(currentDate, DATE_TIME_FORMAT),
            datumZahlung: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a Rechnung', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datumRechnung: format(currentDate, DATE_TIME_FORMAT),
            datumZahlung: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            datumRechnung: currentDate,
            datumZahlung: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Rechnung', async () => {
        const returnedFromService = Object.assign(
          {
            betrag: 1,
            datumRechnung: format(currentDate, DATE_TIME_FORMAT),
            datumZahlung: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datumRechnung: currentDate,
            datumZahlung: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of Rechnung', async () => {
        const returnedFromService = Object.assign(
          {
            betrag: 1,
            datumRechnung: format(currentDate, DATE_TIME_FORMAT),
            datumZahlung: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            datumRechnung: currentDate,
            datumZahlung: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        service.retrieve(expected).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a Rechnung', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        service.delete(123).then(res => {
          expect(res.ok);
        });
      });
    });
  });
});
