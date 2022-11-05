import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './locate.reducer';

export const LocateDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const locateEntity = useAppSelector(state => state.locate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locateDetailsHeading">
          <Translate contentKey="macaviApp.locate.detail.title">Locate</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{locateEntity.id}</dd>
          <dt>
            <span id="ciudad">
              <Translate contentKey="macaviApp.locate.ciudad">Ciudad</Translate>
            </span>
          </dt>
          <dd>{locateEntity.ciudad}</dd>
          <dt>
            <span id="departamento">
              <Translate contentKey="macaviApp.locate.departamento">Departamento</Translate>
            </span>
          </dt>
          <dd>{locateEntity.departamento}</dd>
          <dt>
            <span id="pais">
              <Translate contentKey="macaviApp.locate.pais">Pais</Translate>
            </span>
          </dt>
          <dd>{locateEntity.pais}</dd>
        </dl>
        <Button tag={Link} to="/locate" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/locate/${locateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocateDetail;
