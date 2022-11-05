import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './factura.reducer';

export const FacturaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const facturaEntity = useAppSelector(state => state.factura.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="facturaDetailsHeading">
          <Translate contentKey="macaviApp.factura.detail.title">Factura</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{facturaEntity.id}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="macaviApp.factura.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{facturaEntity.descripcion}</dd>
          <dt>
            <span id="fechaFactura">
              <Translate contentKey="macaviApp.factura.fechaFactura">Fecha Factura</Translate>
            </span>
          </dt>
          <dd>
            {facturaEntity.fechaFactura ? <TextFormat value={facturaEntity.fechaFactura} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="fechaVencimiento">
              <Translate contentKey="macaviApp.factura.fechaVencimiento">Fecha Vencimiento</Translate>
            </span>
          </dt>
          <dd>
            {facturaEntity.fechaVencimiento ? (
              <TextFormat value={facturaEntity.fechaVencimiento} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tipoPago">
              <Translate contentKey="macaviApp.factura.tipoPago">Tipo Pago</Translate>
            </span>
          </dt>
          <dd>{facturaEntity.tipoPago}</dd>
          <dt>
            <span id="totalFactura">
              <Translate contentKey="macaviApp.factura.totalFactura">Total Factura</Translate>
            </span>
          </dt>
          <dd>{facturaEntity.totalFactura}</dd>
          <dt>
            <Translate contentKey="macaviApp.factura.cliente">Cliente</Translate>
          </dt>
          <dd>{facturaEntity.cliente ? facturaEntity.cliente.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/factura" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/factura/${facturaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FacturaDetail;
