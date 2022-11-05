import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tipo-dni.reducer';

export const TipoDniDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tipoDniEntity = useAppSelector(state => state.tipoDni.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoDniDetailsHeading">
          <Translate contentKey="macaviApp.tipoDni.detail.title">TipoDni</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoDniEntity.id}</dd>
          <dt>
            <span id="siglasDni">
              <Translate contentKey="macaviApp.tipoDni.siglasDni">Siglas Dni</Translate>
            </span>
          </dt>
          <dd>{tipoDniEntity.siglasDni}</dd>
          <dt>
            <span id="nombreDni">
              <Translate contentKey="macaviApp.tipoDni.nombreDni">Nombre Dni</Translate>
            </span>
          </dt>
          <dd>{tipoDniEntity.nombreDni}</dd>
        </dl>
        <Button tag={Link} to="/tipo-dni" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-dni/${tipoDniEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoDniDetail;
