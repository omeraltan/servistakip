import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import PersonelUpdatePage from './personel-update.page-object';

const expect = chai.expect;
export class PersonelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.personel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-personel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class PersonelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('personel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('personel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreatePersonel() {
    await this.createButton.click();
    return new PersonelUpdatePage();
  }

  async deletePersonel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const personelDeleteDialog = new PersonelDeleteDialog();
    await waitUntilDisplayed(personelDeleteDialog.deleteModal);
    expect(await personelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.personel.delete.question/);
    await personelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(personelDeleteDialog.deleteModal);

    expect(await isVisible(personelDeleteDialog.deleteModal)).to.be.false;
  }
}
