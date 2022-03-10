import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './protokol-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProtokolTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const protokolTemelEntity = useAppSelector(state => state.protokolTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="protokolTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.protokolTemel.detail.title">ProtokolTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{protokolTemelEntity.id}</dd>
          <dt>
            <span id="sureDurum">
              <Translate contentKey="servisTakipApp.protokolTemel.sureDurum">Sure Durum</Translate>
            </span>
          </dt>
          <dd>{protokolTemelEntity.sureDurum}</dd>
          <dt>
            <span id="sureBitisTarihi">
              <Translate contentKey="servisTakipApp.protokolTemel.sureBitisTarihi">Sure Bitis Tarihi</Translate>
            </span>
          </dt>
          <dd>
            {protokolTemelEntity.sureBitisTarihi ? (
              <TextFormat value={protokolTemelEntity.sureBitisTarihi} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="protokolNu">
              <Translate contentKey="servisTakipApp.protokolTemel.protokolNu">Protokol Nu</Translate>
            </span>
          </dt>
          <dd>{protokolTemelEntity.protokolNu}</dd>
          <dt>
            <span id="olurYaziNu">
              <Translate contentKey="servisTakipApp.protokolTemel.olurYaziNu">Olur Yazi Nu</Translate>
            </span>
          </dt>
          <dd>{protokolTemelEntity.olurYaziNu}</dd>
          <dt>
            <span id="protokolImzaTarihi">
              <Translate contentKey="servisTakipApp.protokolTemel.protokolImzaTarihi">Protokol Imza Tarihi</Translate>
            </span>
          </dt>
          <dd>
            {protokolTemelEntity.protokolImzaTarihi ? (
              <TextFormat value={protokolTemelEntity.protokolImzaTarihi} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="protokolAciklamasi">
              <Translate contentKey="servisTakipApp.protokolTemel.protokolAciklamasi">Protokol Aciklamasi</Translate>
            </span>
          </dt>
          <dd>{protokolTemelEntity.protokolAciklamasi}</dd>
        </dl>
        <Button tag={Link} to="/protokol-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/protokol-temel/${protokolTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProtokolTemelDetail;
