import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tablo-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TabloTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tabloTemelEntity = useAppSelector(state => state.tabloTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tabloTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.tabloTemel.detail.title">TabloTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tabloTemelEntity.id}</dd>
          <dt>
            <span id="gercekVeritabani">
              <Translate contentKey="servisTakipApp.tabloTemel.gercekVeritabani">Gercek Veritabani</Translate>
            </span>
          </dt>
          <dd>{tabloTemelEntity.gercekVeritabani}</dd>
          <dt>
            <span id="gercekLogTablo">
              <Translate contentKey="servisTakipApp.tabloTemel.gercekLogTablo">Gercek Log Tablo</Translate>
            </span>
          </dt>
          <dd>{tabloTemelEntity.gercekLogTablo}</dd>
          <dt>
            <span id="gercekaciklamasi">
              <Translate contentKey="servisTakipApp.tabloTemel.gercekaciklamasi">Gercekaciklamasi</Translate>
            </span>
          </dt>
          <dd>{tabloTemelEntity.gercekaciklamasi}</dd>
          <dt>
            <span id="testVeritabani">
              <Translate contentKey="servisTakipApp.tabloTemel.testVeritabani">Test Veritabani</Translate>
            </span>
          </dt>
          <dd>{tabloTemelEntity.testVeritabani}</dd>
          <dt>
            <span id="testLogTablo">
              <Translate contentKey="servisTakipApp.tabloTemel.testLogTablo">Test Log Tablo</Translate>
            </span>
          </dt>
          <dd>{tabloTemelEntity.testLogTablo}</dd>
          <dt>
            <span id="testaciklamasi">
              <Translate contentKey="servisTakipApp.tabloTemel.testaciklamasi">Testaciklamasi</Translate>
            </span>
          </dt>
          <dd>{tabloTemelEntity.testaciklamasi}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.tabloTemel.servis">Servis</Translate>
          </dt>
          <dd>{tabloTemelEntity.servis ? tabloTemelEntity.servis.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tablo-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tablo-temel/${tabloTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TabloTemelDetail;
