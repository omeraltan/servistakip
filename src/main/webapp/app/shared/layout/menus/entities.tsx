import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/servis-is">
      <Translate contentKey="global.menu.entities.servisIs" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/servis-temel">
      <Translate contentKey="global.menu.entities.servisTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/protokol-temel">
      <Translate contentKey="global.menu.entities.protokolTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/irtibat-temel">
      <Translate contentKey="global.menu.entities.irtibatTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/metod-temel">
      <Translate contentKey="global.menu.entities.metodTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/proje-temel">
      <Translate contentKey="global.menu.entities.projeTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/servis-proje-temel">
      <Translate contentKey="global.menu.entities.servisProjeTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/personel">
      <Translate contentKey="global.menu.entities.personel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/kurum-temel">
      <Translate contentKey="global.menu.entities.kurumTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sorun-temel">
      <Translate contentKey="global.menu.entities.sorunTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tablo-temel">
      <Translate contentKey="global.menu.entities.tabloTemel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/klasor-temel">
      <Translate contentKey="global.menu.entities.klasorTemel" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
