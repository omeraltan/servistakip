import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './servis-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ServisTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const servisTemelEntity = useAppSelector(state => state.servisTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servisTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.servisTemel.detail.title">ServisTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.id}</dd>
          <dt>
            <span id="servisAdi">
              <Translate contentKey="servisTakipApp.servisTemel.servisAdi">Servis Adi</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.servisAdi}</dd>
          <dt>
            <span id="servisUrl">
              <Translate contentKey="servisTakipApp.servisTemel.servisUrl">Servis Url</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.servisUrl}</dd>
          <dt>
            <span id="servisVeriTip">
              <Translate contentKey="servisTakipApp.servisTemel.servisVeriTip">Servis Veri Tip</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.servisVeriTip}</dd>
          <dt>
            <span id="aciklamasi">
              <Translate contentKey="servisTakipApp.servisTemel.aciklamasi">Aciklamasi</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.aciklamasi}</dd>
          <dt>
            <span id="dosyasi">
              <Translate contentKey="servisTakipApp.servisTemel.dosyasi">Dosyasi</Translate>
            </span>
          </dt>
          <dd>
            {servisTemelEntity.dosyasi ? (
              <div>
                {servisTemelEntity.dosyasiContentType ? (
                  <a onClick={openFile(servisTemelEntity.dosyasiContentType, servisTemelEntity.dosyasi)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {servisTemelEntity.dosyasiContentType}, {byteSize(servisTemelEntity.dosyasi)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="resmi">
              <Translate contentKey="servisTakipApp.servisTemel.resmi">Resmi</Translate>
            </span>
          </dt>
          <dd>
            {servisTemelEntity.resmi ? (
              <div>
                {servisTemelEntity.resmiContentType ? (
                  <a onClick={openFile(servisTemelEntity.resmiContentType, servisTemelEntity.resmi)}>
                    <img
                      src={`data:${servisTemelEntity.resmiContentType};base64,${servisTemelEntity.resmi}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {servisTemelEntity.resmiContentType}, {byteSize(servisTemelEntity.resmi)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="servisSekli">
              <Translate contentKey="servisTakipApp.servisTemel.servisSekli">Servis Sekli</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.servisSekli}</dd>
          <dt>
            <span id="servisTipi">
              <Translate contentKey="servisTakipApp.servisTemel.servisTipi">Servis Tipi</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.servisTipi}</dd>
          <dt>
            <span id="baglantiSekli">
              <Translate contentKey="servisTakipApp.servisTemel.baglantiSekli">Baglanti Sekli</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.baglantiSekli}</dd>
          <dt>
            <span id="servisDurum">
              <Translate contentKey="servisTakipApp.servisTemel.servisDurum">Servis Durum</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.servisDurum}</dd>
          <dt>
            <span id="anlikSorgu">
              <Translate contentKey="servisTakipApp.servisTemel.anlikSorgu">Anlik Sorgu</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.anlikSorgu}</dd>
          <dt>
            <span id="yiginVeri">
              <Translate contentKey="servisTakipApp.servisTemel.yiginVeri">Yigin Veri</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.yiginVeri}</dd>
          <dt>
            <span id="gds">
              <Translate contentKey="servisTakipApp.servisTemel.gds">Gds</Translate>
            </span>
          </dt>
          <dd>{servisTemelEntity.gds}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.servisTemel.protokol">Protokol</Translate>
          </dt>
          <dd>{servisTemelEntity.protokol ? servisTemelEntity.protokol.id : ''}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.servisTemel.bakanlik">Bakanlik</Translate>
          </dt>
          <dd>{servisTemelEntity.bakanlik ? servisTemelEntity.bakanlik.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/servis-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servis-temel/${servisTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServisTemelDetail;
